package com.relatosdepapel.orders_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//Usamos este DTO para que el usuario no se invente en el POST el precio
public record CreateOrderItemRequest(
        @NotNull(message = "El id del libro es obligatorio")
        Long bookId,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad debe ser mayor que 0")
        Integer quantity
) {
}
