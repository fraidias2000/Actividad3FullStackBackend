package com.relatosdepapel.catalogue_service.exception;


public class DuplicateISBNBookException extends RuntimeException {

    public DuplicateISBNBookException(String message) {
        super(message);
    }
}

