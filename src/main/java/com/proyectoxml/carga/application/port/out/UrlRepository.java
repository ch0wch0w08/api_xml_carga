package com.proyectoxml.carga.application.port.out;

import com.proyectoxml.carga.domain.model.Url;
import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface  UrlRepository {
    
    public Mono<Url> saveUrl(Url url);
    public Mono<Boolean> existsByUrl(String urlValue);
    public Mono<Boolean> existsByUrlExcludingId(String urlValue, Long id);
    public Mono<Url> getById(Long id);
    public Mono<PaginationResponse<Url>> getAll(PaginationQuery query,UrlFilter urlFilter);
    public Flux<Integer> getYears();
    public Mono<Void> deleteById(Long id);
    public Mono<Integer> update(Long id, String UrlValue, Integer CantidadRegistros);

}
