package com.example.air_conditioning_server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AirConditioningListener {

    @RabbitListener(queues = "house.air-conditioning.notification")
    public void actionsAirConditioning(AirConditioning airConditioning){
        System.out.println("air-conditioning state: " + airConditioning.getStatus());
        System.out.println("air-conditioning temperature: " + airConditioning.getTemperatura());

        if(airConditioning.getTemperatura() <= 16 || airConditioning.getTemperatura() >= 27 ){
            throw  new RuntimeException("Temperatura inválida");
        }
    }
}
