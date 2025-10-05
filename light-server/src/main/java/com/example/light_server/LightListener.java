package com.example.light_server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LightListener {
    @RabbitListener(queues = "house.light.notification")
    public void actionsLight(Light light) {
        System.out.println("light state: " + light.getStatus());
    }
}
