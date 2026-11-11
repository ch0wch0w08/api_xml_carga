package com.proyectoxml.carga.domain.exceptions;


public class IncorrectUrlFormatException extends RuntimeException{

    public IncorrectUrlFormatException (String url){
        super("Format Url: " + url + " is incorrect");
    }

}
