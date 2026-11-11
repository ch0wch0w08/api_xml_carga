package com.proyectoxml.job.infrastructure.adapter;

import org.springframework.stereotype.Component;
import com.proyectoxml.job.aplication.port.out.ProcessJobPort;
import com.proyectoxml.job.domain.enums.ETLJobStatus;
import com.proyectoxml.job.domain.model.ETLEvent;
import com.proyectoxml.job.domain.model.ETLJob;
import com.proyectoxml.job.infrastructure.config.ETLProperties;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class PythonProcessAdapter implements ProcessJobPort {

    private final ETLProperties properties;
    private final ObjectMapper objectMapper;
    private final Map<UUID, RunningPythonJob> jobs = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public PythonProcessAdapter(ETLProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<UUID> start() {

        return Mono.fromCallable(() -> {

            if (jobs.values().stream().anyMatch(job -> !job.isFinished())) {
                throw new IllegalStateException(
                    "Ya existe una ejecución ETL en curso"
                );
            }

            UUID jobId = UUID.randomUUID();

            ProcessBuilder builder = new ProcessBuilder(
                properties.getExecutable(),
                "-u",
                properties.getScriptPath(),
                "--job-id",
                jobId.toString()
            );

            builder.directory(Path.of(properties.getWorkingDirectory()).toFile());
            
            if (properties.getDatabaseConnection() != null
                && !properties.getDatabaseConnection().isBlank()) {
                builder.environment().put(
                    "MEF_DB_CONN_STR",
                    properties.getDatabaseConnection()
                );
            }

            builder.redirectErrorStream(false);
            Process process = builder.start();

            RunningPythonJob job = new RunningPythonJob(
                jobId,
                process
            );
            jobs.put(jobId, job);

            publish(job, ETLEvent.log(
                "PROCESS_STARTED",
                "Proceso Python iniciado"
            ));

            CompletableFuture<Void> stdout = CompletableFuture.runAsync(
                () -> readStream(process.getInputStream(), job, false),
                executor
            );

            CompletableFuture<Void> stderr = CompletableFuture.runAsync(
                () -> readStream(process.getErrorStream(), job, true),
                executor
            );

            CompletableFuture.runAsync(
                () -> monitorProcess(job, stdout, stderr),
                executor
            );

            return jobId;

        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void readStream(InputStream input, RunningPythonJob job, boolean errorStream){

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (errorStream) {
                    publish(job, ETLEvent.log("STDERR", line));
                    continue;
                }
                publish(job, parsePythonEvent(line));
            }
        } catch (Exception ex) {
            publish(job, ETLEvent.log("STREAM_ERROR",ex.getMessage()));
        }

    }

    private ETLEvent parsePythonEvent(String line) {

        try {
            Map<String, Object> data = objectMapper.readValue(line,new TypeReference<>() {});
            
            String type = String.valueOf(
                data.getOrDefault("tipo", "PYTHON_LOG")
            );
            String message = String.valueOf(
                data.getOrDefault("mensaje", line)
            );

            return new ETLEvent(
                type,
                Instant.now(),
                message,
                data
            );

        } catch (Exception ex) {
            return ETLEvent.log("PYTHON_LOG", line);
        }
    }

    private void monitorProcess(RunningPythonJob job,CompletableFuture<Void> stdout,CompletableFuture<Void> stderr){
        try {
            int exitCode = job.process().waitFor();

            stdout.join();
            stderr.join();

            job.setExitCode(exitCode);
            if (job.isCancellationRequested()) {

                job.setStatus(ETLJobStatus.CANCELLED);
                publish(job, ETLEvent.log(
                    "PROCESS_CANCELLED",
                    "Proceso cancelado correctamente"
                ));

            } else if (exitCode == 0) {

                job.setStatus(ETLJobStatus.SUCCEEDED);
                publish(job, ETLEvent.log(
                    "PROCESS_COMPLETED",
                    "Python terminó correctamente"
                ));

            } else {

                job.setStatus(ETLJobStatus.FAILED);
                publish(job, ETLEvent.log(
                    "PROCESS_FAILED",
                    "Python terminó con código " + exitCode
                ));
            }

        } catch (InterruptedException ex) {

            Thread.currentThread().interrupt();
            job.setStatus(ETLJobStatus.FAILED);

            publish(job, ETLEvent.log(
                "PROCESS_MONITOR_ERROR",
                "Se interrumpió la supervisión del proceso"
            ));

        } finally {
            job.complete();
        }
    }

    @Override
    public Mono<Void> cancel(UUID jobId) {

        return Mono.fromRunnable(() -> {

            RunningPythonJob job = jobs.get(jobId);
            if (job == null) {
                throw new IllegalArgumentException("No existe la ejecución " + jobId);
            }

            synchronized (job) {

                if (job.isFinished()) {
                    return;
                }
                if (job.isCancellationRequested()) {
                    return;
                }

                job.requestCancellation();
                job.setStatus(ETLJobStatus.CANCELLING);

                publish(job, ETLEvent.log(
                    "CANCELLATION_REQUESTED",
                    "Solicitando cancelación de Python"
                ));

                job.process().destroy();

                try {
                    boolean terminated = job.process().waitFor(properties.getCancelGraceSeconds(),TimeUnit.SECONDS);
                    if (!terminated) {
                        publish(job, ETLEvent.log(
                            "FORCE_TERMINATION",
                            "Se agotó el tiempo de espera. Forzando terminación."
                        ));
                        job.process().descendants().forEach(
                            ProcessHandle::destroyForcibly
                        );
                        job.process().destroyForcibly();
                    }

                } catch (InterruptedException ex) {

                    Thread.currentThread().interrupt();
                    job.process().destroyForcibly();

                    throw new RuntimeException(
                        "Se interrumpió la cancelación",
                        ex
                    );
                }

            }

        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Flux<ETLEvent> stream(UUID jobId) {

        RunningPythonJob job = jobs.get(jobId);
        if (job == null) {
            return Flux.error(
                new IllegalArgumentException("Ejecución no encontrada")
            );
        }
        return job.events();
    }

    @Override
    public Mono<ETLJob> getJob(UUID jobId) {

        return Mono.fromCallable(() -> {
            RunningPythonJob job = jobs.get(jobId);
            if (job == null) {
                throw new IllegalArgumentException("Ejecución no encontrada");
            }

            return new ETLJob(
                job.jobId(),
                job.getIdEjecucion(),
                job.getStatus(),
                job.getExitCode()
            );
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void publish(RunningPythonJob job, ETLEvent event) {

        if ("ETL_INICIADO".equals(event.type()) && event.data().get("idEjecucion") != null) {
            Object id = event.data().get("idEjecucion");
            job.setIdEjecucion(Long.valueOf(id.toString()));
        }

        job.emit(event);
    }
}