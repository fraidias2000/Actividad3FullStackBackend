package com.relatosdepapel.orders_service.controller;

import com.relatosdepapel.orders_service.dto.request.CreateOrderRequest;
import com.relatosdepapel.orders_service.model.Order;
import com.relatosdepapel.orders_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Registrar una compra
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(
            @Valid @RequestBody CreateOrderRequest order,
            @RequestHeader("accessToken") String jwtToken
    ) {
        return orderService.create(order, jwtToken);
    }

    // Obtener una orden por id
    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    // Recuperar ordenes recientes de un usuario
    @GetMapping("/users/{userId}/recent")
    public List<Order> findRecentOrdersByUserId(@PathVariable String userId) {
        return orderService.findRecentOrdersByUserId(userId);
    }
}
