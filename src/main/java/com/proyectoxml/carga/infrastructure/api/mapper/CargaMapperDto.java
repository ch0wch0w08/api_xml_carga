package com.proyectoxml.carga.infrastructure.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.proyectoxml.carga.application.command.create.CreateUrlRequest;
import com.proyectoxml.carga.application.command.update.UpdateUrlRequest;
import com.proyectoxml.carga.infrastructure.api.dto.UrlCreateDto;
import com.proyectoxml.carga.infrastructure.api.dto.UrlUpdateDto;

@Mapper(componentModel = "spring")
public interface CargaMapperDto {


    @Mapping(target = "UrlValue", source = "urlValue")
    @Mapping(target = "CantidadRegistros", source = "cantidadRegistros")
    CreateUrlRequest toCreateRequest(UrlCreateDto dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "UrlValue", source = "dto.urlValue")
    @Mapping(target = "CantidadRegistros", source = "dto.cantidadRegistros")
    UpdateUrlRequest toUpdateRequest(Long id, UrlUpdateDto dto);
}
