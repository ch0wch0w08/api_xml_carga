package com.proyectoxml.mef.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.proyectoxml.mef.infrastructure.persistence.entity.ClasificadorGastoEntity;

public interface ClasificadorGastoR2dbcRepository extends R2dbcRepository<ClasificadorGastoEntity, Long> {}
