package com.supasoft.itemservice.event;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event published when an item is deleted
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDeletedEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long itemId;
    private String itemCode;
    private String itemName;
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

