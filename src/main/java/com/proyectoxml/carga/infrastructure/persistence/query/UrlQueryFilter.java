package com.proyectoxml.carga.infrastructure.persistence.query;

import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;

@FunctionalInterface 
public interface UrlQueryFilter {
    
    public void apply(UrlFilter filter, SqlQueryBuilder builder);
}
