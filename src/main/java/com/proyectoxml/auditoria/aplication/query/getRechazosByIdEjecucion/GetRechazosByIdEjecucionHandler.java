package com.proyectoxml.auditoria.aplication.query.getRechazosByIdEjecucion;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaRechazoResponse;
import com.proyectoxml.auditoria.aplication.port.out.RepositoryAuditoria;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetRechazosByIdEjecucionHandler implements RequestHandler<GetRechazosByIdEjecucionRequest, PaginationResponse<AuditoriaRechazoResponse>> {

    private final RepositoryAuditoria repositoryAuditoria;

    @Override
    public Mono<PaginationResponse<AuditoriaRechazoResponse>> handler(GetRechazosByIdEjecucionRequest request) {
        return repositoryAuditoria.obtenerRechazos(request.getIdEjecucion(), request.getQuery())
            .map(page -> new PaginationResponse<>(
                page.getContent().stream().map(AuditoriaRechazoResponse::from).toList(),
                page.getLimit(),
                page.isHasNext(),
                page.getNextLastSortValue(),
                page.getNextLastId()
            ));
    }

    @Override
    public Class<GetRechazosByIdEjecucionRequest> getRequestType() {
        return GetRechazosByIdEjecucionRequest.class;
    }
}
