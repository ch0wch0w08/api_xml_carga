package com.proyectoxml.auditoria.aplication.query.getAniosEje;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectoxml.auditoria.infrastructure.persistence.repository.AuditoriaDetalleSearchRepository;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetAniosEjeHandler implements RequestHandler<GetAniosEjeRequest, List<Integer>> {

    private final AuditoriaDetalleSearchRepository repository;

    @Override
    public Mono<List<Integer>> handler(GetAniosEjeRequest request) {
        return repository.findDistinctAnios().collectList();
    }

    @Override
    public Class<GetAniosEjeRequest> getRequestType() {
        return GetAniosEjeRequest.class;
    }
}
