package com.example.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String RETRY_EXCHANGE = "retry.exchange";
    public static final String DLQ_EXCHANGE = "dlq.exchange";

    public static final String MAIN_QUEUE = "main.queue";
    public static final String RETRY_QUEUE = "retry.queue";
    public static final String DLQ_QUEUE = "dlq.queue";

    public static final String MAIN_ROUTING_KEY = "main.#";
    public static final String RETRY_ROUTING_KEY = "retry.#";
    public static final String DLQ_ROUTING_KEY = "dlq.#";


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    @Bean
    public TopicExchange retryExchange() {
        return new TopicExchange(RETRY_EXCHANGE);
    }

    @Bean
    public TopicExchange dlqExchange() {
        return new TopicExchange(DLQ_EXCHANGE);
    }

    @Bean
    public Queue mainQueue() {
        Map<String, Object> args = new HashMap<>();
        // Quando der erro (nack), envia para retry exchange
        args.put("x-dead-letter-exchange", RETRY_EXCHANGE);
        args.put("x-dead-letter-routing-key", "retry.main");

        return new Queue(MAIN_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding mainQueueBinding() {
        return BindingBuilder
                .bind(mainQueue())
                .to(topicExchange())
                .with(MAIN_ROUTING_KEY);
    }

    @Bean
    public Queue retryQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);

        args.put("x-dead-letter-exchange", TOPIC_EXCHANGE);
        args.put("x-dead-letter-routing-key", "main.retry");

        return new Queue(RETRY_QUEUE, true, false, false, args);
    }

    @Bean
    public Binding retryQueueBinding() {
        return BindingBuilder
                .bind(retryQueue())
                .to(retryExchange())
                .with(RETRY_ROUTING_KEY);
    }

    @Bean
    public Queue dlqQueue() {
        return new Queue(DLQ_QUEUE, true);
    }

    @Bean
    public Binding dlqQueueBinding() {
        return BindingBuilder
                .bind(dlqQueue())
                .to(dlqExchange())
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> applicationEventApplicationListener(
            RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
