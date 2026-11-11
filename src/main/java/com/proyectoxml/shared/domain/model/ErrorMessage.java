package com.proyectoxml.shared.domain.model;

public class ErrorMessage {

    private final String message;
    private final String exception;
    private final String error;
    private final String path;

    public ErrorMessage(String message, String exception, String error, String path) {
        this.message = message;
        this.exception = exception;
        this.error = error;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
