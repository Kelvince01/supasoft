package com.supasoft.itemservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Event Configuration
 */
@Configuration
public class EventConfig {
    
    @Value("${rabbitmq.exchange.item:item.exchange}")
    private String itemExchange;
    
    @Value("${rabbitmq.queue.item.created:item.created.queue}")
    private String itemCreatedQueue;
    
    @Value("${rabbitmq.queue.item.updated:item.updated.queue}")
    private String itemUpdatedQueue;
    
    @Value("${rabbitmq.queue.item.deleted:item.deleted.queue}")
    private String itemDeletedQueue;
    
    @Value("${rabbitmq.routingkey.item.created:item.created}")
    private String itemCreatedRoutingKey;
    
    @Value("${rabbitmq.routingkey.item.updated:item.updated}")
    private String itemUpdatedRoutingKey;
    
    @Value("${rabbitmq.routingkey.item.deleted:item.deleted}")
    private String itemDeletedRoutingKey;
    
    @Bean
    public DirectExchange itemExchange() {
        return new DirectExchange(itemExchange);
    }
    
    @Bean
    public Queue itemCreatedQueue() {
        return new Queue(itemCreatedQueue, true);
    }
    
    @Bean
    public Queue itemUpdatedQueue() {
        return new Queue(itemUpdatedQueue, true);
    }
    
    @Bean
    public Queue itemDeletedQueue() {
        return new Queue(itemDeletedQueue, true);
    }
    
    @Bean
    public Binding itemCreatedBinding() {
        return BindingBuilder.bind(itemCreatedQueue())
                .to(itemExchange())
                .with(itemCreatedRoutingKey);
    }
    
    @Bean
    public Binding itemUpdatedBinding() {
        return BindingBuilder.bind(itemUpdatedQueue())
                .to(itemExchange())
                .with(itemUpdatedRoutingKey);
    }
    
    @Bean
    public Binding itemDeletedBinding() {
        return BindingBuilder.bind(itemDeletedQueue())
                .to(itemExchange())
                .with(itemDeletedRoutingKey);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}

