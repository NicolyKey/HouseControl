package com.example.light_server;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String MAIN_QUEUE = "house.light.command";
    public static final String MAIN_EXCHANGE = "topic.exchange";
    public static final String NOTIFICATION_QUEUE = "house.notification.light";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MAIN_EXCHANGE);
    }

    @Bean
    public Queue lightQueue() {
        return new Queue(MAIN_QUEUE);
    }

    @Bean
    public Queue lightNotificationQueue() {
        return new Queue(NOTIFICATION_QUEUE);
    }

    @Bean
    public Binding mainBinding() {
        return BindingBuilder.bind(lightQueue())
                .to(topicExchange())
                .with(MAIN_QUEUE);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(lightNotificationQueue())
                .to(topicExchange())
                .with(NOTIFICATION_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
