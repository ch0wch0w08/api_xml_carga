package com.proyectoxml.mef.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.Query;

import com.proyectoxml.mef.infrastructure.persistence.entity.TiempoEntity;

import reactor.core.publisher.Flux;

public interface TiempoR2dbcRepository extends R2dbcRepository<TiempoEntity, Long> {

    @Query("SELECT DISTINCT AnioEje FROM mef.Dim_Tiempo ORDER BY AnioEje DESC")
    Flux<Integer> findDistinctAnios();

    @Query("SELECT DISTINCT MesEje FROM mef.Dim_Tiempo ORDER BY MesEje ASC")
    Flux<Integer> findDistinctMeses();
}
