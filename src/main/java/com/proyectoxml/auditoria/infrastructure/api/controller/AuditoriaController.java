package com.proyectoxml.auditoria.infrastructure.api.controller;

import com.proyectoxml.auditoria.aplication.port.in.AuditoriaDetalleResponse;
import com.proyectoxml.auditoria.aplication.port.in.AuditoriaEjecucionResponse;
import com.proyectoxml.auditoria.aplication.port.in.AuditoriaRechazoResponse;
import com.proyectoxml.auditoria.aplication.query.getAllEjecuciones.GetAllEjecucionesRequest;
import com.proyectoxml.auditoria.aplication.query.getDetallesByIdEjecucion.GetDetallesByIdEjecucionRequest;
import com.proyectoxml.auditoria.aplication.query.getRechazosByIdEjecucion.GetRechazosByIdEjecucionRequest;
import com.proyectoxml.auditoria.aplication.query.getAniosEje.GetAniosEjeRequest;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaDetalleFilter;
import com.proyectoxml.auditoria.domain.model.pagination.AuditoriaEjecucionFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auditoria")
public class AuditoriaController {

    private final Mediator mediator;

    @GetMapping("/anios")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<Integer>> getAniosEje() {
        return mediator.dispatch(new GetAniosEjeRequest());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaginationResponse<AuditoriaEjecucionResponse>> getEjecuciones(
        @ModelAttribute PaginationQuery query,
        @ModelAttribute AuditoriaEjecucionFilter filter
    ) {
        return mediator.dispatch(new GetAllEjecucionesRequest(query, filter));
    }

    @GetMapping("/{idEjecucion}/detalles")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaginationResponse<AuditoriaDetalleResponse>> getDetalles(
        @PathVariable Long idEjecucion,
        @ModelAttribute PaginationQuery query,
        @ModelAttribute AuditoriaDetalleFilter filter
    ) {
        return mediator.dispatch(new GetDetallesByIdEjecucionRequest(idEjecucion, query, filter));
    }

    @GetMapping("/{idEjecucion}/rechazos")
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaginationResponse<AuditoriaRechazoResponse>> getRechazos(
        @PathVariable Long idEjecucion,
        @ModelAttribute PaginationQuery query
    ) {
        return mediator.dispatch(new GetRechazosByIdEjecucionRequest(idEjecucion, query));
    }
}
