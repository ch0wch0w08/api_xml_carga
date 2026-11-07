package com.proyectoxml.mef.aplication.query.getAll;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectoxml.mef.aplication.port.in.GastoPresupuestalResponse;
import com.proyectoxml.mef.aplication.port.out.RepositoryMef;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service 
@RequiredArgsConstructor 
public class getAllHandler implements RequestHandler <getAllRequest, PaginationResponse<GastoPresupuestalResponse>> {

    private final RepositoryMef repositoryMef;

    @Override
    public Mono<PaginationResponse<GastoPresupuestalResponse>> handler(getAllRequest request) {
        return repositoryMef.getFacts(request.getFilter(), request.getQuery())
        .map(pg -> {
            List<GastoPresupuestalResponse> response = pg.getContent().stream().map(c -> GastoPresupuestalResponse.toGastoPresupuestalResponse(c)).toList();

            return new PaginationResponse<GastoPresupuestalResponse>(
                response,
                pg.getLimit(),
                pg.isHasNext(),
                pg.getNextLastSortValue(),
                pg.getNextLastId()
            );
        });
        
    }

    @Override
    public Class<getAllRequest> getRequestType() {
        return getAllRequest.class;
    }
    
}
