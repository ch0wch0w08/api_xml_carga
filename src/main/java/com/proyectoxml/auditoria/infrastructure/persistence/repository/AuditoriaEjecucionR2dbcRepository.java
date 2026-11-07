package com.proyectoxml.auditoria.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaEjecucionEntity;

public interface AuditoriaEjecucionR2dbcRepository extends R2dbcRepository<AuditoriaEjecucionEntity, Integer> {}
