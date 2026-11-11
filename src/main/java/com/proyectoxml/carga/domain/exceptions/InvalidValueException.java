package com.proyectoxml.carga.domain.exceptions;

public class InvalidValueException extends RuntimeException {
    
    public InvalidValueException(Object value){
        super("Value " + value + " is invalid");
    }

}
