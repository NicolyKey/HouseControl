package com.example.light_server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LightListener {

    private Light light = new Light(false);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "house.light.command")
    public void actionsLight(boolean status) {
        light.setStatus(status);
        System.out.println("light state: " + light.getStatus());

        rabbitTemplate.convertAndSend(
                "topic.exchange",
                "house.notification.light",
                "Luz " + (status ? "ligada" : "desligada")
        );
    }
}
