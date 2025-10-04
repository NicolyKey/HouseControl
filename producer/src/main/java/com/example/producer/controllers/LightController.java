package com.example.producer.controllers;

import com.example.producer.entities.Light;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/light")
public class LightController {
    Light light = new Light(false);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("{status}")
    public Light changeStatus(@PathVariable boolean status) {
        light.setStatus(status);
        String routingKey = "light.notification";
        rabbitTemplate.convertAndSend("topic.exchange", routingKey,light);
        return light;
    }

    @GetMapping()
    public Light getLight() {
        return light;
    }
}
