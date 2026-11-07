package com.proyectoxml.mef.infrastructure.persistence.repository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.proyectoxml.mef.infrastructure.persistence.entity.UbigeoEntity;


public interface UbigeoR2dbcRepository extends R2dbcRepository<UbigeoEntity, Long> {}
