package com.supasoft.partnerservice.exception;

import com.supasoft.common.exception.ResourceNotFoundException;

/**
 * Exception thrown when a supplier is not found
 */
public class SupplierNotFoundException extends ResourceNotFoundException {
    
    public SupplierNotFoundException(Long id) {
        super("Supplier", "id", id);
    }
    
    public SupplierNotFoundException(String field, Object value) {
        super("Supplier", field, value);
    }
}

