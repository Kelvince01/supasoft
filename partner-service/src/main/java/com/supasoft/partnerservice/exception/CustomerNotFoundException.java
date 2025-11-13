package com.supasoft.partnerservice.exception;

import com.supasoft.common.exception.ResourceNotFoundException;

/**
 * Exception thrown when a customer is not found
 */
public class CustomerNotFoundException extends ResourceNotFoundException {
    
    public CustomerNotFoundException(Long id) {
        super("Customer", "id", id);
    }
    
    public CustomerNotFoundException(String field, Object value) {
        super("Customer", field, value);
    }
}

