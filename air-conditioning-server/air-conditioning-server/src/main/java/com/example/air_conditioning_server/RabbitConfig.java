package com.example.air_conditioning_server;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    public static final String RETRY_QUEUE = "air-conditioning.notification.retry";
    public static final String MAIN_EXCHANGE = "topic.exchange";
    public static final String RETRY_EXCHANGE = "retry.air.exchange";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MAIN_EXCHANGE);
    }

    @Bean
    public TopicExchange retryExchange() {
        return new TopicExchange(RETRY_EXCHANGE);
    }

    @Bean
    public Queue acQueue() {
        return new Queue(MAIN_QUEUE);
    }

    @Bean
    public Queue retryQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10_000);
        args.put("x-dead-letter-exchange", MAIN_EXCHANGE);
        args.put("x-dead-letter-routing-key", MAIN_QUEUE);
        return new Queue(RETRY_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding mainBinding() {
        return BindingBuilder.bind(acQueue())
                .to(topicExchange())
                .with(MAIN_QUEUE);
    }

    @Bean
    public Binding retryBinding() {
        return BindingBuilder.bind(retryQueue())
                .to(retryExchange())
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