package com.relatosdepapel.comms_service.service;


import com.relatosdepapel.comms_service.dto.event.EmailEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailEvent event) {
       /* SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.to());
        message.setSubject(event.subject());
        message.setText(event.body());

        mailSender.send(message);*/
        System.out.println("FUNCIONA" + event.body());
    }
}
