package com.proyectoxml.carga.application.command.create;

import org.springframework.stereotype.Service;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.carga.domain.exceptions.UrlAlreadyExistsException;
import com.proyectoxml.carga.domain.model.Url;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service 
@RequiredArgsConstructor 
public class CreateUrlHandler implements RequestHandler<CreateUrlRequest,UrlResponse> {

    private final UrlRepository repository;

    @Override
    public Mono<UrlResponse> handler(CreateUrlRequest request) {
        return repository.existsByUrl(request.getUrlValue())
            .flatMap(exists -> {
                if (exists) {
                    return Mono.error(new UrlAlreadyExistsException(request.getUrlValue()));
                }
                return repository.saveUrl(Url.createUrl(request.getUrlValue(), request.getCantidadRegistros()));
            })
            .map(UrlResponse::toUrlResponse);
    }

    @Override
    public Class<CreateUrlRequest> getRequestType() {
       return CreateUrlRequest.class;
    }
    
}
