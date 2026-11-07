package com.proyectoxml.auditoria.aplication.query.getAllEjecuciones;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaEjecucionResponse;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaEjecucionFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllEjecucionesRequest implements Request<PaginationResponse<AuditoriaEjecucionResponse>> {
    private PaginationQuery query;
    private AuditoriaEjecucionFilter filter;
}
