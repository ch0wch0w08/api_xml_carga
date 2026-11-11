package com.proyectoxml.carga.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data 
public class UrlCreateDto {
    
    @NotBlank
    private String UrlValue;
 
    @Positive 
    private Integer CantidadRegistros;
}
