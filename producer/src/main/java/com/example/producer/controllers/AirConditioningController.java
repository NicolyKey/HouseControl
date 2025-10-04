package com.example.producer.controllers;


import com.example.producer.entities.AirConditioning;
import com.example.producer.enums.Temperature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/air-conditioning")
public class AirConditioningController {
    AirConditioning airConditioning = new AirConditioning(false, 23);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("{status}")
    public AirConditioning changeStatus(@PathVariable boolean status){
        airConditioning.setStatus(status);
        String routingKey = "air-conditioning.notification";
        rabbitTemplate.convertAndSend("topic.exchange", routingKey, airConditioning);
        return airConditioning;
    }

    @PostMapping("/temperature/{temperature}")
    public AirConditioning changeTemperature(@PathVariable Temperature temperature){
        if (Temperature.INCREASE == temperature){
            airConditioning.increaseTemperature();
        }
        if (Temperature.DECREASE == temperature){
            airConditioning.decreaseTemperature();
        }
        String routingKey = "air-conditioning.notification";
        rabbitTemplate.convertAndSend("topic.exchange", routingKey, airConditioning);
        return airConditioning;
    }
}
