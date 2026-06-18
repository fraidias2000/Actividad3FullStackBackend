package com.relatosdepapel.orders_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty(message = "La orden debe tener al menos un libro")
        List<@Valid CreateOrderItemRequest> items
) {
}
