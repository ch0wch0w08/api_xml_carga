package com.proyectoxml.carga.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.proyectoxml.carga.infrastructure.persistence.entity.UrlEntity;

import reactor.core.publisher.Mono;

public interface UrlR2dbcRepository extends R2dbcRepository<UrlEntity, Long> {

    @Modifying
    @Query("DELETE FROM mef.Fact_Gasto_Presupuestal WHERE Id_Url = :id")
    Mono<Integer> deleteFactsByUrlId(Long id);

    @Query("SELECT COUNT(1) FROM dbo.URLs WHERE URL = :urlValue")
    Mono<Integer> countByUrl(String urlValue);

    @Query("SELECT COUNT(1) FROM dbo.URLs WHERE URL = :urlValue AND Id_Url <> :id")
    Mono<Integer> countByUrlExcludingId(String urlValue, Long id);

    @Modifying
    @Query("UPDATE dbo.URLs SET URL = COALESCE(:urlValue, URL), Cantidad_Registros = COALESCE(:cantidadRegistros, Cantidad_Registros) WHERE Id_Url = :id")
    Mono<Integer> patchUpdate(Long id, String urlValue, Integer cantidadRegistros);
    
}
