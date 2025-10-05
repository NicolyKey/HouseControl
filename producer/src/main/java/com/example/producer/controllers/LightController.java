package com.example.producer.controllers;

import com.example.producer.entities.Light;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/light")
public class LightController {
    public static final String EXCHANGE = "topic.exchange";
    public static final String MAIN_QUEUE = "house.light.command";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("{status}")
    public String changeStatus(@PathVariable boolean status) {
        rabbitTemplate.convertAndSend(EXCHANGE, MAIN_QUEUE,status);
        return "Comando enviado: " + (status ? "Ligar a luz" : "Desligar a luz");
    }
}
