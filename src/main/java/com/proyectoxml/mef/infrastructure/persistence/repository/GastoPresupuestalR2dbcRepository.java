package com.proyectoxml.mef.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.proyectoxml.mef.infrastructure.persistence.entity.GastoPresupuestalEntity;


public interface GastoPresupuestalR2dbcRepository extends R2dbcRepository<GastoPresupuestalEntity, Long> {}
