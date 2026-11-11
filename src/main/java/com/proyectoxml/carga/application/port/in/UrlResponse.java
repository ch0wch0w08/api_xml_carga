package com.proyectoxml.carga.application.port.in;

import com.proyectoxml.carga.domain.model.Url;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
public class UrlResponse {

    private Long idUrl;

    private String urlValue;
    private Integer anio;
    private Integer cantidadRegistros;


    public static UrlResponse toUrlResponse(Url url){
        return 
        UrlResponse.builder()
            .anio(url.getAnio())
            .cantidadRegistros(url.getCantidadRegistros())
            .urlValue(url.getUrlValue())
            .idUrl(url.getIdUrl())
        .build();
    }
    
}
