package com.proyectoxml.mef.infrastructure.persistence.query;

import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.shared.infrastructure.query.SqlQueryBuilder;

@FunctionalInterface 
public interface  GastoPresupuestalQueryFilter {
    public void apply (GastoPresupuestalFilter filter, SqlQueryBuilder builder);
}
