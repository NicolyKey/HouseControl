package com.example.notification_server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NotificationListener {
    @RabbitListener(queues = "house.notification")
    public void actionsLight(Object notification) {
        if (Objects.isNull(notification)) {
            System.out.println("é nulo");
        }
        System.out.println("Notification message: " + notification);
    }
}
