package com.proyectoxml.carga.application.query.getById;

import com.proyectoxml.carga.application.port.in.UrlResponse;
import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor 
public class GetByIdRequest implements Request<UrlResponse>{
    Long id;
}
