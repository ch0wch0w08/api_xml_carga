package com.proyectoxml.auditoria.aplication.query.getDetallesByIdEjecucion;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaDetalleResponse;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaDetalleFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetDetallesByIdEjecucionRequest implements Request<PaginationResponse<AuditoriaDetalleResponse>> {
    private Long idEjecucion;
    private PaginationQuery query;
    private AuditoriaDetalleFilter filter;
}
