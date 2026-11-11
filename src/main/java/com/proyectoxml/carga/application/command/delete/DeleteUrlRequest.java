package com.proyectoxml.carga.application.command.delete;

import com.proyectoxml.shared.infrastructure.mediator.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor 
@Data 
public class DeleteUrlRequest implements Request<Void> {
    private Long id;    
}
