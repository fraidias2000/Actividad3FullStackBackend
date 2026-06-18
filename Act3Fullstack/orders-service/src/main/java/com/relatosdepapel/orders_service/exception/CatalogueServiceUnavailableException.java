package com.relatosdepapel.orders_service.exception;

public class CatalogueServiceUnavailableException extends RuntimeException{
    public CatalogueServiceUnavailableException(String message) {
        super(message);
    }
}
