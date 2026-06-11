package com.relatosdepapel.orders_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

//Este DTO representa la respuesta que devuelve catalogue-service cuando llamamos a /api/books/{id}.
public record CatalogueBookResponse(
        Long id,
        String title,
        String author,
        LocalDate publicationDate,
        String category,
        String isbn,
        Integer rating,
        Boolean visible,
        Integer stock,
        BigDecimal price
) {
}
