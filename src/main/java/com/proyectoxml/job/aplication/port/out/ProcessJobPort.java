package com.proyectoxml.job.aplication.port.out;

import java.util.UUID;

import com.proyectoxml.job.domain.model.ETLEvent;
import com.proyectoxml.job.domain.model.ETLJob;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProcessJobPort {

    Mono<UUID> start();

    Mono<Void> cancel(UUID jobId);

    Flux<ETLEvent> stream(UUID jobId);

    Mono<ETLJob> getJob(UUID jobId);
}

