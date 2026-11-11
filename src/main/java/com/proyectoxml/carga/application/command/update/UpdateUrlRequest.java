package com.proyectoxml.carga.application.command.update;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class UpdateUrlRequest implements Request<UrlResponse>{
    private Long id;
    private String UrlValue;
    private Integer CantidadRegistros; 
}
