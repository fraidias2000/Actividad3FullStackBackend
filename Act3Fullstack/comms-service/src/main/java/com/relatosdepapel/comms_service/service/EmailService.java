package com.relatosdepapel.comms_service.service;

import com.relatosdepapel.comms_service.dto.event.EmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:}")
    private String defaultFrom;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailEvent event) {
        validateEmailEvent(event);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.to());
        message.setSubject(event.subject());
        message.setText(event.body());

        String from = resolveFromAddress();
        if (StringUtils.hasText(from)) {
            message.setFrom(from);
        }

        try {
            mailSender.send(message);
            log.info("Email sent successfully to {} with subject '{}'", event.to(), event.subject());
        } catch (MailException exception) {
            log.error("Error sending email to {} with subject '{}'", event.to(), event.subject(), exception);
            throw exception;
        }
    }

    private void validateEmailEvent(EmailEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Email event cannot be null");
        }
        if (!StringUtils.hasText(event.to())) {
            throw new IllegalArgumentException("Email recipient cannot be empty");
        }
        if (!StringUtils.hasText(event.subject())) {
            throw new IllegalArgumentException("Email subject cannot be empty");
        }
        if (!StringUtils.hasText(event.body())) {
            throw new IllegalArgumentException("Email body cannot be empty");
        }
    }

    private String resolveFromAddress() {
        if (StringUtils.hasText(defaultFrom)) {
            return defaultFrom;
        }
        return mailUsername;
    }
}
