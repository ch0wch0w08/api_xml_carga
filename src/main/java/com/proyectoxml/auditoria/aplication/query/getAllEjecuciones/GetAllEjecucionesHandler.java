package com.proyectoxml.auditoria.aplication.query.getAllEjecuciones;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaEjecucionResponse;
import com.proyectoxml.auditoria.aplication.port.out.RepositoryAuditoria;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetAllEjecucionesHandler implements RequestHandler<GetAllEjecucionesRequest, PaginationResponse<AuditoriaEjecucionResponse>> {

    private final RepositoryAuditoria repositoryAuditoria;

    @Override
    public Mono<PaginationResponse<AuditoriaEjecucionResponse>> handler(GetAllEjecucionesRequest request) {
        return repositoryAuditoria.obtenerEjecuciones(request.getFilter(), request.getQuery())
            .map(page -> new PaginationResponse<>(
                page.getContent().stream().map(AuditoriaEjecucionResponse::from).toList(),
                page.getLimit(),
                page.isHasNext(),
                page.getNextLastSortValue(),
                page.getNextLastId()
            ));
    }

    @Override
    public Class<GetAllEjecucionesRequest> getRequestType() {
        return GetAllEjecucionesRequest.class;
    }
}
