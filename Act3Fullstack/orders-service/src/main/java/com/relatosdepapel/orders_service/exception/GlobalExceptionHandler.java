package com.relatosdepapel.orders_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

//SIRVE PARA PERSONALIZAR LAS EXCEPCIONES
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<Map<String, String>> handleBookNotFound(InvalidOrderException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error " + HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(CatalogueServiceUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateISBN(CatalogueServiceUnavailableException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error " + HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}
