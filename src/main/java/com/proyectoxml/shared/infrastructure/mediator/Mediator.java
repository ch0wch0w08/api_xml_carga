package com.proyectoxml.shared.infrastructure.mediator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component 
@Slf4j 
public class Mediator {
    
    private final Map<Class<?>, RequestHandler<?,?>> mapRequestsHandler;

    public Mediator (List<RequestHandler<?,?>> requestsHandler){
        this.mapRequestsHandler = requestsHandler.stream().collect(Collectors.toMap(
           r -> r.getRequestType(), Function.identity()
        ));
    }

    public <R extends Request<T>, T> Mono<T> dispatch(R request){

        RequestHandler<R,T> requestHandler = (RequestHandler<R,T>) mapRequestsHandler.get(request.getClass());
        if(requestHandler == null){
            log.error("Request handler not found, class {}", request.getClass().getSimpleName());
            throw new RuntimeException("Error: Request handler not found");     
        }
        return requestHandler.handler(request);
        
    }

}
