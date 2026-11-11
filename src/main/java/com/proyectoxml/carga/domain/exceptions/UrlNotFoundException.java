package com.proyectoxml.carga.domain.exceptions;

public class UrlNotFoundException extends RuntimeException {
    
    public UrlNotFoundException(Long id){
        super("Url not found with id: " + id);
    }
}
