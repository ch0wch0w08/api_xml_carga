package com.proyectoxml.job.infrastructure.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectoxml.job.aplication.service.ETLApplicationService;
import com.proyectoxml.job.domain.model.ETLEvent;
import com.proyectoxml.job.domain.model.ETLJob;

@RestController
@RequestMapping("/api/etl/ejecuciones")
public class ETLController {

    private final ETLApplicationService service;

    public ETLController(ETLApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<StartETLResponse>> start() {
        return service
                .start()
                .map(jobId -> ResponseEntity.accepted()
                .body(new StartETLResponse(jobId,"RUNNING","Proceso ETL iniciado")));
    }


    @GetMapping("/{jobId}")
    public Mono<ETLJob> getJob(@PathVariable UUID jobId) {
        return service.getJob(jobId);
    }

    @GetMapping(value = "/{jobId}/eventos", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ETLEvent>> events(@PathVariable UUID jobId) {
        return service.stream(jobId)
                .map(event -> ServerSentEvent.<ETLEvent>builder()
                .id(event.timestamp().toString())
                .event(event.type())
                .data(event)
                .build());
    }


    @DeleteMapping("/{jobId}")
    public Mono<ResponseEntity<Void>> cancel(@PathVariable UUID jobId) {
        return service.cancel(jobId).thenReturn(ResponseEntity.accepted().<Void>build());
    }

    public record StartETLResponse(
            UUID jobId,
            String status,
            String message
    ) {
    }
}
