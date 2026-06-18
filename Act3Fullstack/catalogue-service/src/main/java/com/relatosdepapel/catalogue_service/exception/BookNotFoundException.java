package com.relatosdepapel.catalogue_service.exception;


public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}

