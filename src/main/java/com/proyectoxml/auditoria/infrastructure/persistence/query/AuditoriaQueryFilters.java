package com.proyectoxml.auditoria.infrastructure.persistence.query;

import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaDetalleFilter;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaEjecucionFilter;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaQueryFilters {

    public void apply(AuditoriaEjecucionFilter filter, SqlQueryBuilder query) {
        if (filter != null && filter.getEstadoEjecucion() != null) {
            query.and("ae.Estado_Ejecucion = :estadoEjecucion ").param("estadoEjecucion", filter.getEstadoEjecucion());
        }
    }

    public void apply(AuditoriaDetalleFilter filter, SqlQueryBuilder query) {
        if (filter == null) {
            return;
        }
        if (filter.getAnioEje() != null) {
            query.and("ad.AnioEje = :anioEje ").param("anioEje", filter.getAnioEje());
        }
        if (filter.getEstadoUrl() != null) {
            query.and("ad.Estado_URL = :estadoUrl ").param("estadoUrl", filter.getEstadoUrl());
        }
        if (filter.getHttpStatus() != null) {
            query.and("ad.Http_Status = :httpStatus ").param("httpStatus", filter.getHttpStatus());
        }
    }
}
