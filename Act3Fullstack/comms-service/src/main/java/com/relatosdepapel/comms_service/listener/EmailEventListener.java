package com.relatosdepapel.comms_service.listener;

import com.relatosdepapel.comms_service.dto.event.EmailEvent;
import com.relatosdepapel.comms_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailEventListener {

    private final EmailService emailService;

    public EmailEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    //Escucha de la cola de email y envia un correo
    @RabbitListener(queues = "${app.rabbitmq.email-queue}")
    public void handleEmailEvent(EmailEvent event) {
        emailService.sendEmail(event);
    }
}
