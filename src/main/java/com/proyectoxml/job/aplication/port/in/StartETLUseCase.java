package com.proyectoxml.job.aplication.port.in;

import java.util.UUID;

import reactor.core.publisher.Mono;

public interface StartETLUseCase {
    Mono<UUID> start();
}
