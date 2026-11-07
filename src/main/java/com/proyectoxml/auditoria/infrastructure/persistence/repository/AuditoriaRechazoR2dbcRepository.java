package com.proyectoxml.auditoria.infrastructure.persistence.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaRechazoEntity;

public interface AuditoriaRechazoR2dbcRepository extends R2dbcRepository<AuditoriaRechazoEntity, Long> {}
