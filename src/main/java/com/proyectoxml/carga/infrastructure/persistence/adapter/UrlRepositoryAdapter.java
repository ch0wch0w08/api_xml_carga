package com.proyectoxml.carga.infrastructure.persistence.adapter;

import org.springframework.stereotype.Repository;

import com.proyectoxml.carga.application.port.out.UrlRepository;
import com.proyectoxml.carga.domain.model.Url;
import com.proyectoxml.carga.domain.model.UrlFilter;
import com.proyectoxml.carga.infrastructure.persistence.mapper.UrlMapper;
import com.proyectoxml.carga.infrastructure.persistence.repository.UrlR2dbcRepository;
import com.proyectoxml.carga.infrastructure.persistence.repository.UrlSearchRepository;
import com.proyectoxml.shared.domain.model.PaginationQuery;
import com.proyectoxml.shared.domain.model.PaginationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Repository 
@RequiredArgsConstructor 
@Slf4j 
public class UrlRepositoryAdapter implements UrlRepository {

    private final UrlR2dbcRepository repository;
    private final UrlSearchRepository searchRepository;
    private final UrlMapper mapper;
    

    @Override
    public Mono<Url> saveUrl(Url url) {
        return repository.save(mapper.toEntity(url)).map(u -> mapper.toDomain(u));
    }

    @Override
    public Mono<Boolean> existsByUrl(String urlValue) {
        return repository.countByUrl(urlValue).map(count -> count > 0);
    }

    @Override
    public Mono<Boolean> existsByUrlExcludingId(String urlValue, Long id) {
        return repository.countByUrlExcludingId(urlValue, id).map(count -> count > 0);
    }

    @Override
    public Mono<Url> getById(Long id) {
        return repository.findById(id).map(u -> mapper.toDomain(u));
    }

    public Mono<Integer> update(Long id, String UrlValue, Integer CantidadRegistros){
        return repository.patchUpdate(id,UrlValue, CantidadRegistros);
    }

    @Override
    public Mono<PaginationResponse<Url>> getAll(PaginationQuery query, UrlFilter urlFilter) {

        int limitInitial = query.getLimit();
        query.setLimit(limitInitial + 1);

        return Mono.zip(
            searchRepository.search(query, urlFilter).map(mapper::toDomain).collectList(),
            searchRepository.count(urlFilter)
        ).map(tuple -> {
                var u1 = tuple.getT1();
                
                boolean hasNext = u1.size() > limitInitial;
                if(hasNext){
                    u1.remove(u1.size() - 1);
                }

                Object nextLastSortValue = null;
                Long nextLastId = null;

                if(!u1.isEmpty()){
                    Url lastUrl = u1.get(u1.size() -1);
                    nextLastId = lastUrl.getIdUrl();
                    nextLastSortValue = ExtraerValorSort(lastUrl,query.getSortBy());
                }

                return new PaginationResponse<Url>(
                    u1,
                    limitInitial,
                    hasNext,
                    nextLastSortValue,
                    nextLastId,
                    tuple.getT2()
                );
            });
    }

    @Override
    public Flux<Integer> getYears() {
        return searchRepository.findDistinctYears();
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteFactsByUrlId(id)
            .then(repository.deleteById(id));
    }

    private Object ExtraerValorSort(Url registro, String sortBy){
        if(sortBy == null){return registro.getIdUrl();}

        switch (sortBy) {
            case "IdUrl", "idUrl": return registro.getIdUrl();
            case "Url", "url", "urlValue": return registro.getUrlValue();
            case "Anio", "anio": return registro.getAnio();
            case "CantidadRegitros", "cantidadRegistros": return registro.getCantidadRegistros();
            default: return registro.getIdUrl();
        }
    }

}