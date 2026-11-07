package com.proyectoxml.mef.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.proyectoxml.mef.infrastructure.persistence.entity.EntidadEntity;


public interface EntidadR2dbcRepository extends R2dbcRepository<EntidadEntity, Long> {}
