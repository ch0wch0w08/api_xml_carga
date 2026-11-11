package com.proyectoxml.job.aplication.port.in;

import java.util.UUID;
import com.proyectoxml.job.domain.model.ETLEvent;
import reactor.core.publisher.Flux;

public interface StreamETLEventsUseCase {
    Flux<ETLEvent> stream(UUID jobId);
}
