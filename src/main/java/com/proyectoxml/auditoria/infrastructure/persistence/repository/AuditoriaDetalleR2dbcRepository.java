package com.proyectoxml.auditoria.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaDetalleEntity;

public interface AuditoriaDetalleR2dbcRepository extends R2dbcRepository<AuditoriaDetalleEntity, Integer> {}
