package com.proyectoxml.auditoria.infrastructure.persistence.mapper;

import com.proyectoxml.auditoria.domain.model.Auditoria_detalle;
import com.proyectoxml.auditoria.domain.model.Auditoria_ejecucion;
import com.proyectoxml.auditoria.domain.model.Auditoria_rechazos;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaDetalleEntity;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaEjecucionEntity;
import com.proyectoxml.auditoria.infrastructure.persistence.entity.AuditoriaRechazoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    Auditoria_ejecucion toDomain(AuditoriaEjecucionEntity source);

    Auditoria_detalle toDomain(AuditoriaDetalleEntity source);
    
    Auditoria_rechazos toDomain(AuditoriaRechazoEntity source);
    
}
