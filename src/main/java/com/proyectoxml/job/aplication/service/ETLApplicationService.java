package com.proyectoxml.job.aplication.service;

import org.springframework.stereotype.Service;

import com.proyectoxml.job.aplication.port.in.CancelETLUseCase;
import com.proyectoxml.job.aplication.port.in.StartETLUseCase;
import com.proyectoxml.job.aplication.port.in.StreamETLEventsUseCase;
import com.proyectoxml.job.aplication.port.out.ProcessJobPort;
import com.proyectoxml.job.domain.model.ETLEvent;
import com.proyectoxml.job.domain.model.ETLJob;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ETLApplicationService implements StartETLUseCase, CancelETLUseCase, StreamETLEventsUseCase {

    private final ProcessJobPort processJobPort;

    public ETLApplicationService(ProcessJobPort processJobPort) {
        this.processJobPort = processJobPort;
    }

    @Override
    public Mono<UUID> start() {
        return processJobPort.start();
    }

    @Override
    public Mono<Void> cancel(UUID jobId) {
        return processJobPort.cancel(jobId);
    }

    @Override
    public Flux<ETLEvent> stream(UUID jobId) {
        return processJobPort.stream(jobId);
    }

    public Mono<ETLJob> getJob(UUID jobId) {
        return processJobPort.getJob(jobId);
    }
    
}