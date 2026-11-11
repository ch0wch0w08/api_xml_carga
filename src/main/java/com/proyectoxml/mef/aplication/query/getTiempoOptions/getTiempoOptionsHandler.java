package com.proyectoxml.mef.aplication.query.getTiempoOptions;

import com.proyectoxml.mef.aplication.port.in.TiempoOptionsResponse;
import com.proyectoxml.mef.infrastructure.persistence.repository.TiempoR2dbcRepository;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class getTiempoOptionsHandler
        implements RequestHandler<getTiempoOptionsRequest, TiempoOptionsResponse> {

    private final TiempoR2dbcRepository repository;

    @Override
    public Mono<TiempoOptionsResponse> handler(getTiempoOptionsRequest request) {
        return Mono.zip(
                repository.findDistinctAnios().collectList(),
                repository.findDistinctMeses().collectList()
        ).map(values -> TiempoOptionsResponse.builder()
                .anios(values.getT1())
                .meses(values.getT2())
                .build());
    }

    @Override
    public Class<getTiempoOptionsRequest> getRequestType() {
        return getTiempoOptionsRequest.class;
    }
}
