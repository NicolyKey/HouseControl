package com.example.light_server;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LightListener {

    private Light light = new Light(false);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "house.light.command", ackMode = "MANUAL")
    public void actionsLight(boolean status, Channel channel, Message message) {
        try {
            light.setStatus(status);
            System.out.println("light state: " + light.getStatus());

            rabbitTemplate.convertAndSend(
                    "topic.exchange",
                    "house.notification.light",
                    "Luz " + (status ? "ligada" : "desligada")
            );

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
