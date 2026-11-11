package com.proyectoxml.carga.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.proyectoxml.carga.domain.model.Url;
import com.proyectoxml.carga.infrastructure.persistence.entity.UrlEntity;

@Mapper(componentModel = "spring")
public interface  UrlMapper {
    
    @Mapping(target = "urlValue", source = "urlValue")
    @Mapping(target = "anio", source = "anio")
    @Mapping(target = "cantidadRegistros", source = "cantidadRegistros")
    Url toDomain (UrlEntity url);


    UrlEntity toEntity(Url url);

}
