package com.proyectoxml.mef.aplication.query.getById;

import org.springframework.stereotype.Service;

import com.proyectoxml.mef.aplication.port.in.GastoPresupuestoFullResponse;
import com.proyectoxml.mef.aplication.port.out.RepositoryMef;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service 
@RequiredArgsConstructor 
public class getByIdHandler implements RequestHandler<getByIdRequest,GastoPresupuestoFullResponse>{

    private final RepositoryMef repositoryMef;

    @Override
    public Mono<GastoPresupuestoFullResponse> handler(getByIdRequest request) {
    
        return repositoryMef.getFactById(request.getId())
        .switchIfEmpty(Mono.error(new RuntimeException("Id not found")))
        .map(gp -> GastoPresupuestoFullResponse.toGastoPresupuestalFullResponse(gp));
         
    }

    @Override
    public Class<getByIdRequest> getRequestType() {
        return getByIdRequest.class;
    }
    
}
