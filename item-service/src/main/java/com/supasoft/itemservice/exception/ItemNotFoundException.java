package com.supasoft.itemservice.exception;

import com.supasoft.common.exception.ResourceNotFoundException;

/**
 * Exception thrown when an item is not found
 */
public class ItemNotFoundException extends ResourceNotFoundException {
    
    public ItemNotFoundException(String message) {
        super(message);
    }
    
    public ItemNotFoundException(Long id) {
        super("Item not found with id: " + id);
    }
    
    public ItemNotFoundException(String field, String value) {
        super("Item not found with " + field + ": " + value);
    }
}

