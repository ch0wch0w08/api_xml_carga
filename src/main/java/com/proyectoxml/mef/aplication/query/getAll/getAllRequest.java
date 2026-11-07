package com.proyectoxml.mef.aplication.query.getAll;
import com.proyectoxml.mef.aplication.port.in.GastoPresupuestalResponse;
import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class getAllRequest implements Request<PaginationResponse<GastoPresupuestalResponse>> {

    private PaginationQuery query;
    private GastoPresupuestalFilter filter;
}
