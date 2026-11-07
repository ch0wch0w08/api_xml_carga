package com.proyectoxml.auditoria.aplication.port.out;

import com.proyectoxml.auditoria.domain.model.Auditoria_detalle;
import com.proyectoxml.auditoria.domain.model.Auditoria_ejecucion;
import com.proyectoxml.auditoria.domain.model.Auditoria_rechazos;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaDetalleFilter;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaEjecucionFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import reactor.core.publisher.Mono;

public interface RepositoryAuditoria {

    Mono<PaginationResponse<Auditoria_ejecucion>> obtenerEjecuciones(
        AuditoriaEjecucionFilter filter,
        PaginationQuery query
    );

    Mono<PaginationResponse<Auditoria_detalle>> obtenerDetalles(
        Long idEjecucion,
        AuditoriaDetalleFilter filter,
        PaginationQuery query
    );

    Mono<PaginationResponse<Auditoria_rechazos>> obtenerRechazos(
        Long idEjecucion,
        PaginationQuery query
    );
}
