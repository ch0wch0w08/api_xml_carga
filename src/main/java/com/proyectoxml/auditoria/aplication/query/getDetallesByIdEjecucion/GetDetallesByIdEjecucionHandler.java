package com.proyectoxml.auditoria.aplication.query.getDetallesByIdEjecucion;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaDetalleResponse;
import com.proyectoxml.auditoria.aplication.port.out.RepositoryAuditoria;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetDetallesByIdEjecucionHandler implements RequestHandler<GetDetallesByIdEjecucionRequest, PaginationResponse<AuditoriaDetalleResponse>> {

    private final RepositoryAuditoria repositoryAuditoria;

    @Override
    public Mono<PaginationResponse<AuditoriaDetalleResponse>> handler(GetDetallesByIdEjecucionRequest request) {
        return repositoryAuditoria.obtenerDetalles(request.getIdEjecucion(), request.getFilter(), request.getQuery())
            .map(page -> new PaginationResponse<>(
                page.getContent().stream().map(AuditoriaDetalleResponse::from).toList(),
                page.getLimit(),
                page.isHasNext(),
                page.getNextLastSortValue(),
                page.getNextLastId(),
                page.getTotalItems()
            ));
    }

    @Override
    public Class<GetDetallesByIdEjecucionRequest> getRequestType() {
        return GetDetallesByIdEjecucionRequest.class;
    }
}
