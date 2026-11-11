package com.proyectoxml.carga.application.query.getById;

import org.springframework.stereotype.Service;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.carga.domain.exceptions.UrlNotFoundException;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service("cargaGetByIdHandler")
@RequiredArgsConstructor
public class GetByIdHandler implements RequestHandler<GetByIdRequest, UrlResponse> {

    private final UrlRepository repository;

    @Override
    public Mono<UrlResponse> handler(GetByIdRequest request) {
        return repository.getById(request.getId())
            .switchIfEmpty(Mono.error(new UrlNotFoundException(request.getId())))
            .map(UrlResponse::toUrlResponse);
    }

    @Override
    public Class<GetByIdRequest> getRequestType() {
        return GetByIdRequest.class;
    }
}
