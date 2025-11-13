package com.supasoft.pricingservice.exception;

import com.supasoft.common.exception.ResourceNotFoundException;

/**
 * Exception thrown when a price is not found
 */
public class PriceNotFoundException extends ResourceNotFoundException {
    
    public PriceNotFoundException(String message) {
        super(message);
    }
    
    public PriceNotFoundException(Long itemId, Long priceTypeId) {
        super("Price not found for item ID: " + itemId + " and price type ID: " + priceTypeId);
    }
    
    public PriceNotFoundException(Long id) {
        super("Price not found with ID: " + id);
    }
}

