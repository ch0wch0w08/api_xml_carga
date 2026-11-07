package com.proyectoxml.mef.aplication.port.out;
import com.proyectoxml.mef.domain.model.GastoPresupuestal;
import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import reactor.core.publisher.Mono;

public interface RepositoryMef {

    Mono<GastoPresupuestal> getFactById(Long id);
    Mono<PaginationResponse<GastoPresupuestal>> getFacts(GastoPresupuestalFilter filtro, PaginationQuery query);

}
