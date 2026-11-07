package com.proyectoxml.shared.infrastructure.mediator;

import reactor.core.publisher.Mono;

public interface RequestHandler <R extends Request<T>, T > {
    
    public Mono<T> handler (R request);

    public Class<R> getRequestType();

}
