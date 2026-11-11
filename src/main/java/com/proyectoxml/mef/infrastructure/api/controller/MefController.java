package com.proyectoxml.mef.infrastructure.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.proyectoxml.mef.aplication.port.in.GastoPresupuestalResponse;
import com.proyectoxml.mef.aplication.port.in.GastoPresupuestoFullResponse;
import com.proyectoxml.mef.aplication.port.in.TiempoOptionsResponse;
import com.proyectoxml.mef.aplication.query.getAll.getAllRequest;
import com.proyectoxml.mef.aplication.query.getById.getByIdRequest;
import com.proyectoxml.mef.aplication.query.getTiempoOptions.getTiempoOptionsRequest;
import com.proyectoxml.mef.domain.model.pagination.GastoPresupuestalFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor  
@RequestMapping ("/api/v1/mef")
public class MefController {
    
    private final Mediator mediator;

    @GetMapping("/tiempos")
    @ResponseStatus (HttpStatus.OK)
    public Mono<TiempoOptionsResponse> getTiempoOptions() {
        return mediator.dispatch(new getTiempoOptionsRequest());
    }

    @GetMapping("/{id}")
    @ResponseStatus (HttpStatus.OK)
    public Mono<GastoPresupuestoFullResponse> getGastoPresupuestalById(@PathVariable Long id) {
        return mediator.dispatch(new getByIdRequest(id));
    }

    @GetMapping
    @ResponseStatus (HttpStatus.OK)
    public Mono<PaginationResponse<GastoPresupuestalResponse>> getGastoPresupuestal(@ModelAttribute PaginationQuery query, @ModelAttribute GastoPresupuestalFilter filter ) {
        return mediator.dispatch(new getAllRequest(query,filter));
    }

}
