package com.proyectoxml.job.aplication.port.in;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface  CancelETLUseCase {
    Mono<Void> cancel(UUID jobId);
}
