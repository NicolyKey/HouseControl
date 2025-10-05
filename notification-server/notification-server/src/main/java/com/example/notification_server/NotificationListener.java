package com.example.notification_server;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @RabbitListener(queues = {"house.notification.light", "house.notification.ac"})
    public void actionsLight(String notification) {
        System.out.println("[NOTIFICAÇÃO] " + notification);
    }
}
