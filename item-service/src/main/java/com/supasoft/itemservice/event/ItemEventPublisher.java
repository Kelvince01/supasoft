package com.supasoft.itemservice.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.supasoft.itemservice.entity.Item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Publisher for item-related events to RabbitMQ
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ItemEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.item:item.exchange}")
    private String itemExchange;
    
    @Value("${rabbitmq.routingkey.item.created:item.created}")
    private String itemCreatedRoutingKey;
    
    @Value("${rabbitmq.routingkey.item.updated:item.updated}")
    private String itemUpdatedRoutingKey;
    
    @Value("${rabbitmq.routingkey.item.deleted:item.deleted}")
    private String itemDeletedRoutingKey;
    
    /**
     * Publish item created event
     */
    public void publishItemCreatedEvent(Item item) {
        try {
            ItemCreatedEvent event = ItemCreatedEvent.builder()
                    .itemId(item.getItemId())
                    .itemCode(item.getItemCode())
                    .itemName(item.getItemName())
                    .barcode(item.getBarcode())
                    .build();
            
            rabbitTemplate.convertAndSend(itemExchange, itemCreatedRoutingKey, event);
            log.info("Published ItemCreatedEvent for item ID: {}", item.getItemId());
        } catch (Exception e) {
            log.error("Error publishing ItemCreatedEvent for item ID: {}", item.getItemId(), e);
        }
    }
    
    /**
     * Publish item updated event
     */
    public void publishItemUpdatedEvent(Item item) {
        try {
            ItemUpdatedEvent event = ItemUpdatedEvent.builder()
                    .itemId(item.getItemId())
                    .itemCode(item.getItemCode())
                    .itemName(item.getItemName())
                    .barcode(item.getBarcode())
                    .build();
            
            rabbitTemplate.convertAndSend(itemExchange, itemUpdatedRoutingKey, event);
            log.info("Published ItemUpdatedEvent for item ID: {}", item.getItemId());
        } catch (Exception e) {
            log.error("Error publishing ItemUpdatedEvent for item ID: {}", item.getItemId(), e);
        }
    }
    
    /**
     * Publish item deleted event
     */
    public void publishItemDeletedEvent(Item item) {
        try {
            ItemDeletedEvent event = ItemDeletedEvent.builder()
                    .itemId(item.getItemId())
                    .itemCode(item.getItemCode())
                    .itemName(item.getItemName())
                    .build();
            
            rabbitTemplate.convertAndSend(itemExchange, itemDeletedRoutingKey, event);
            log.info("Published ItemDeletedEvent for item ID: {}", item.getItemId());
        } catch (Exception e) {
            log.error("Error publishing ItemDeletedEvent for item ID: {}", item.getItemId(), e);
        }
    }
}

