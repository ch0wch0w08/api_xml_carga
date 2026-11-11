package com.proyectoxml.shared.infrastructure.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;

import com.proyectoxml.carga.domain.exceptions.IncorrectUrlFormatException;
import com.proyectoxml.carga.domain.exceptions.InvalidValueException;
import com.proyectoxml.carga.domain.exceptions.UrlAlreadyExistsException;
import com.proyectoxml.carga.domain.exceptions.UrlNotFoundException;
import com.proyectoxml.shared.domain.model.ErrorMessage;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<ErrorMessage> handleUrlAlreadyExists(UrlAlreadyExistsException exception,ServerWebExchange exchange) {
        return Mono.just(errorMessage(exception, HttpStatus.CONFLICT, exchange));
    }

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorMessage> handleUrlNotFound(UrlNotFoundException exception,ServerWebExchange exchange) {
        return Mono.just(errorMessage(exception, HttpStatus.NOT_FOUND, exchange));
    }

    @ExceptionHandler({IncorrectUrlFormatException.class, InvalidValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorMessage> handleBadRequest(RuntimeException exception,ServerWebExchange exchange) {
        return Mono.just(errorMessage(exception, HttpStatus.BAD_REQUEST, exchange));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorMessage> handleValidation(WebExchangeBindException exception,ServerWebExchange exchange) {
        String message = exception.getFieldErrors().stream()
            .map(this::formatFieldError)
            .reduce((first, second) -> first + "; " + second)
            .orElse("La solicitud contiene datos inválidos.");

        return Mono.just(new ErrorMessage(
            message,
            WebExchangeBindException.class.getSimpleName(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            exchange.getRequest().getPath().value()
        ));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorMessage> handleMethodValidation(HandlerMethodValidationException exception,ServerWebExchange exchange) {
        return Mono.just(errorMessage(
            exception,
            HttpStatus.BAD_REQUEST,
            exchange
        ));
    }

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorMessage> handleInvalidInput(ServerWebInputException exception,ServerWebExchange exchange) {
        return Mono.just(errorMessage(
            exception,
            HttpStatus.BAD_REQUEST,
            exchange
        ));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorMessage> handleUnexpectedError(Exception exception,ServerWebExchange exchange) {
        return Mono.just(errorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR, exchange));
    }

    private ErrorMessage errorMessage(Exception exception, HttpStatus status, ServerWebExchange exchange) {
        return new ErrorMessage(exception.getMessage(), exception.getClass().getSimpleName(), status.getReasonPhrase(), exchange.getRequest().getPath().value());
    }


    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
