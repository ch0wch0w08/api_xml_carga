package com.proyectoxml.carga.application.command.delete;
import org.springframework.stereotype.Service;

import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.carga.domain.exceptions.UrlNotFoundException;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service 
@RequiredArgsConstructor 
public class DeleteUrlHandler implements  RequestHandler<DeleteUrlRequest,Void>{

    private final UrlRepository repository;

    @Override
    public Mono<Void> handler(DeleteUrlRequest request) {
        return repository.getById(request.getId())
                .switchIfEmpty(Mono.error(new UrlNotFoundException(request.getId())))
                .flatMap(r -> repository.deleteById(request.getId()));
    }

    @Override
    public Class<DeleteUrlRequest> getRequestType() {
        return DeleteUrlRequest.class;
    }

}
