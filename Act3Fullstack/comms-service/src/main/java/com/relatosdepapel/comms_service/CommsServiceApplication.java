package com.relatosdepapel.comms_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class CommsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommsServiceApplication.class, args);
    }

}
