package com.relatosdepapel.orders_service.service;

import com.relatosdepapel.orders_service.dto.event.EmailEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.email-exchange}")
    private String emailExchange;

    @Value("${app.rabbitmq.email-routing-key}")
    private String emailRoutingKey;

    public EmailEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreatedEmail(String userEmail, Long orderId) {
        try {
            EmailEvent event = new EmailEvent(
                    userEmail,
                    "Pedido confirmado - Relatos de Papel",
                    "Tu pedido con ID " + orderId + " ha sido creado correctamente.",
                    Map.of(
                            "orderId", orderId,
                            "eventType", "ORDER_CREATED"
                    )
            );

            rabbitTemplate.convertAndSend(
                    emailExchange,
                    emailRoutingKey,
                    event
            );

            System.out.println("Evento de email publicado en RabbitMQ para pedido " + orderId);

        } catch (Exception e) {
            System.err.println("No se pudo publicar el evento de email: " + e.getMessage());
        }
    }
}