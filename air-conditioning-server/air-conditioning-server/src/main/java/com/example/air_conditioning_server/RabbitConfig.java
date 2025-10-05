package com.example.air_conditioning_server;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    public static final String MAIN_QUEUE = "air-conditioning.notification";
    public static final String RETRY_QUEUE = "air-conditioning.notification.dlq";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String TOPIC_RETRY_EXCHANGE = "topic.exchange.dlx";


    @Bean
    public Queue acQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", TOPIC_RETRY_EXCHANGE);
        args.put("x-dead-letter-routing-key", RETRY_QUEUE);
        return new Queue(MAIN_QUEUE, true, false, false, args);
    }

    @Bean
    public Queue retryQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10_000);
        return new Queue(RETRY_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding mainBinding() {
        TopicExchange exchange = new TopicExchange(TOPIC_EXCHANGE);
        return BindingBuilder
                .bind(acQueue())
                .to(exchange)
                .with(MAIN_QUEUE);
    }

    @Bean
    public Binding retryBinding() {
        TopicExchange exchange = new TopicExchange(TOPIC_RETRY_EXCHANGE);
        return BindingBuilder
                .bind(retryQueue())
                .to(exchange)
                .with(RETRY_QUEUE);
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