package com.relatosdepapel.orders_service.client;

import com.relatosdepapel.orders_service.dto.CatalogueBookResponse;
import com.relatosdepapel.orders_service.dto.CreateOrderRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "catalogue-service")
public interface CatalogueClient {

    //ENDPOINT PARA OBTENER UN LIBRO SEGÚN SU ID
    @GetMapping("/api/books/{id}")
    CatalogueBookResponse findBookById(@PathVariable("id") Long id);

    //ENDPOINT PARA ACTUALIZAR EL STOCK
    @PostMapping ("/api/books/updateStock/{id}")
    CatalogueBookResponse decreaseStock(
            @PathVariable("id") Long id,
            @RequestParam("quantity") Integer quantity
    );
}
