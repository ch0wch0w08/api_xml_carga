package com.proyectoxml.job.infrastructure.adapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.UUID;

import com.proyectoxml.job.domain.enums.ETLJobStatus;
import com.proyectoxml.job.domain.model.ETLEvent;

public class RunningPythonJob {

    private final UUID jobId;
    private final Process process;
    private final Sinks.Many<ETLEvent> sink = Sinks.many().replay().limit(500);
    private volatile ETLJobStatus status = ETLJobStatus.RUNNING;
    private volatile Long idEjecucion;
    private volatile Integer exitCode;
    private volatile boolean cancellationRequested;
    private volatile boolean finished;

    public RunningPythonJob(UUID jobId, Process process) {
        this.jobId = jobId;
        this.process = process;
    }

    public void emit(ETLEvent event) {
        sink.tryEmitNext(event);
    }

    public Flux<ETLEvent> events() {
        return sink.asFlux();
    }

    public void complete() {
        finished = true;
        sink.tryEmitComplete();
    }

    public UUID jobId() {
        return jobId;
    }

    public Process process() {
        return process;
    }

    public ETLJobStatus getStatus() {
        return status;
    }

    public void setStatus(ETLJobStatus status) {
        this.status = status;
    }

    public Long getIdEjecucion() {
        return idEjecucion;
    }

    public void setIdEjecucion(Long idEjecucion) {
        this.idEjecucion = idEjecucion;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public boolean isCancellationRequested() {
        return cancellationRequested;
    }

    public void requestCancellation() {
        this.cancellationRequested = true;
    }

    public boolean isFinished() {
        return finished;
    }
}