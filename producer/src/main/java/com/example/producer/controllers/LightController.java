package com.example.producer.controllers;

import com.example.producer.entities.Light;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/light")
public class LightController {
    Light light = new Light(false);
    public static final String MAIN_QUEUE = "house.light.notification";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("{status}")
    public Light changeStatus(@PathVariable boolean status) {
        light.setStatus(status);
        rabbitTemplate.convertAndSend("topic.exchange", MAIN_QUEUE,light);
        return light;
    }

    @GetMapping()
    public Light getLight() {
        return light;
    }
}
