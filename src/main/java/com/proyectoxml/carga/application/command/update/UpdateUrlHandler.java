package com.proyectoxml.carga.application.command.update;

import org.springframework.stereotype.Service;
import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.carga.domain.exceptions.UrlAlreadyExistsException;
import com.proyectoxml.carga.domain.exceptions.UrlNotFoundException;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service 
@RequiredArgsConstructor 
public class UpdateUrlHandler implements RequestHandler<UpdateUrlRequest,UrlResponse>{

    private final UrlRepository repository;

    public Mono<UrlResponse> handler(UpdateUrlRequest request) {
        return repository.getById(request.getId())
            .switchIfEmpty(Mono.error(new UrlNotFoundException(request.getId())))
            .then(repository.existsByUrlExcludingId(request.getUrlValue(), request.getId()))
            .flatMap(exists -> {
                if (exists) {
                    return Mono.error(new UrlAlreadyExistsException(request.getUrlValue()));
                }
                return repository.update(request.getId(), request.getUrlValue(), request.getCantidadRegistros());
            })
            .filter(rowsUpdated -> rowsUpdated > 0)
            .switchIfEmpty(Mono.error(new UrlNotFoundException(request.getId())))
            .flatMap(rows -> repository.getById(request.getId()))
            .map(u -> UrlResponse.toUrlResponse(u));
    }

    @Override
    public Class<UpdateUrlRequest> getRequestType() {
        return UpdateUrlRequest.class;
    }
    
}
