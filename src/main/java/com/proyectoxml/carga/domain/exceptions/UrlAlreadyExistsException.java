package com.proyectoxml.carga.domain.exceptions;

public class UrlAlreadyExistsException extends RuntimeException {

    public UrlAlreadyExistsException(String url) {
        super("Url already exists: " + url);
    }
}
