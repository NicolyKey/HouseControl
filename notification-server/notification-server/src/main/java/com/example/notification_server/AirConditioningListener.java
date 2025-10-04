package com.example.notification_server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AirConditioningListener {

    @RabbitListener(queues = "air-conditioning.notification")
    public void actionsAirConditioning(AirConditioning airConditioning){
        System.out.println("air-conditioning state: " + airConditioning.getStatus());
        System.out.println("air-conditioning temperature: " + airConditioning.getTemperatura());
    }
}
