package com.relatosdepapel.orders_service.repository;

import com.relatosdepapel.orders_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findTop10ByUserIdOrderByCreatedAtDesc(String userId);
}
