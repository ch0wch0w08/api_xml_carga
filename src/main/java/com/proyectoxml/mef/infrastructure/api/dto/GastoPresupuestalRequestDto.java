package com.proyectoxml.mef.infrastructure.api.dto;

import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;

import lombok.Data;

@Data 
public class GastoPresupuestalRequestDto {
    
    GastoPresupuestalFilter filter;
    PaginationQuery query;
    
}
