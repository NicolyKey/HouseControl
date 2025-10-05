package com.example.notification_server;

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

    public static final String MAIN_QUEUE = "house.notification";
    public static final String MAIN_EXCHANGE = "topic.exchange";

    @Bean
    public Queue houseNotificationQueue() {
        return new Queue(MAIN_QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MAIN_EXCHANGE);
    }


    @Bean
    public Binding houseNotificationBinding() {
        return BindingBuilder.bind(houseNotificationQueue())
                .to(topicExchange())
                .with("house.*");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
