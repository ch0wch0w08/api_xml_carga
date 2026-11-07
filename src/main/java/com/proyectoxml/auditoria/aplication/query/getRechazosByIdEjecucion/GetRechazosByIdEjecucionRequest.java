package com.proyectoxml.auditoria.aplication.query.getRechazosByIdEjecucion;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaRechazoResponse;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class GetRechazosByIdEjecucionRequest implements Request<PaginationResponse<AuditoriaRechazoResponse>> {
    private Long idEjecucion;
    private PaginationQuery query;
}
