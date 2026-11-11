package com.proyectoxml.carga.infrastructure.api.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UrlUpdateDto {

    private String UrlValue;

    @Positive 
    private Integer CantidadRegistros;
}
