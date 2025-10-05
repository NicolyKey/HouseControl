package com.example.air_conditioning_server;

import com.example.air_conditioning_server.enums.Temperature;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirConditioningListener {

    AirConditioning airConditioning = new AirConditioning(false, 23);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "house.air-conditioning.command")
    public void actionsAirConditioning(AirConditioningDTO message){
        try {
            if (message.getStatus() != null)
                airConditioning.setStatus(message.getStatus());

            if (message.getTemperature() != null){
                if (message.getTemperature() == Temperature.INCREASE){
                    if (airConditioning.getTemperatura() < 27)
                        airConditioning.setTemperatura(airConditioning.getTemperatura()+1);
                    else
                        throw  new RuntimeException("Temperatura inválida");
                } else if (message.getTemperature() == Temperature.DECREASE) {
                    if (airConditioning.getTemperatura() >16)
                        airConditioning.setTemperatura(airConditioning.getTemperatura()-1);
                    else
                        throw  new RuntimeException("Temperatura inválida");
                }
            }

            System.out.println("air-conditioning state: " + airConditioning.getStatus());
            System.out.println("air-conditioning temperature: " + airConditioning.getTemperatura());

            rabbitTemplate.convertAndSend("topic.exchange",
                    "house.notification.ac",
                    "Ar-condicionado atualizado: status=" + airConditioning.getStatus()
                            + ", temp=" + airConditioning.getTemperatura());
        } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }
}
