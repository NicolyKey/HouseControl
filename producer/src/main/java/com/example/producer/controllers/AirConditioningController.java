package com.example.producer.controllers;


import com.example.producer.controllers.dto.AirConditioningDTO;
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
    public static final String MAIN_ROUTING_KEY = "house.air-conditioning.command";
    public static final String EXCHANGE = "topic.exchange";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("{status}")
    public String changeStatus(@PathVariable boolean status){
        AirConditioningDTO dto = new AirConditioningDTO(status, null);
        rabbitTemplate.convertAndSend(EXCHANGE, MAIN_ROUTING_KEY, dto);
        return "Comando enviado: " + (status ? "Ligar o ar condicionado" : "Desligar o ar condicionado");
    }

    @PostMapping("/temperature/{temperature}")
    public String changeTemperature(@PathVariable Temperature temperature){
        AirConditioningDTO dto = new AirConditioningDTO(null, temperature);
        rabbitTemplate.convertAndSend(EXCHANGE, MAIN_ROUTING_KEY, dto);
        return "Comando enviado: " + (temperature == Temperature.DECREASE ? "Diminuir a temperatura" : "Aumentar a temperatura");
    }
}
