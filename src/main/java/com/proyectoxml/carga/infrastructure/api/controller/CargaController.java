package com.proyectoxml.carga.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.proyectoxml.carga.application.command.delete.DeleteUrlRequest;
import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.carga.application.query.getAll.GetAllRequest;
import com.proyectoxml.carga.application.query.getById.GetByIdRequest;
import com.proyectoxml.carga.application.query.getYears.GetYearsRequest;
import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.carga.infrastructure.api.dto.UrlCreateDto;
import com.proyectoxml.carga.infrastructure.api.dto.UrlUpdateDto;
import com.proyectoxml.carga.infrastructure.api.mapper.CargaMapperDto;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;
import com.proyectoxml.shared.infrastructure.mediator.Mediator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carga")
@Slf4j 
public class CargaController {

    private final Mediator mediator;
    private final CargaMapperDto mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<PaginationResponse<UrlResponse>> getAll(
        @ModelAttribute PaginationQuery query,
        @ModelAttribute UrlFilter filter
    ) {
        return mediator.dispatch(new GetAllRequest(query, filter));
    }

    @GetMapping("/anios")
    public Mono<java.util.List<Integer>> getYears() {
        return mediator.dispatch(new GetYearsRequest());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UrlResponse> getById(@PathVariable Long id) {
        return mediator.dispatch(new GetByIdRequest(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UrlResponse> create(@Valid @RequestBody UrlCreateDto request) {
        return mediator.dispatch(mapper.toCreateRequest(request));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UrlResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UrlUpdateDto request
    ) {
        return mediator.dispatch(mapper.toUpdateRequest(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return mediator.dispatch(new DeleteUrlRequest(id));
    }
    
}
