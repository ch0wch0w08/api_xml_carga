package com.proyectoxml.carga.application.command.create;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor 
@Data 
public class CreateUrlRequest implements Request<UrlResponse>{

    private String UrlValue;
    private Integer CantidadRegistros;  

}
