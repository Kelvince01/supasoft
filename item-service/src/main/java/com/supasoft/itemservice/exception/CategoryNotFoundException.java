package com.supasoft.itemservice.exception;

import com.supasoft.common.exception.ResourceNotFoundException;

/**
 * Exception thrown when a category is not found
 */
public class CategoryNotFoundException extends ResourceNotFoundException {
    
    public CategoryNotFoundException(String message) {
        super(message);
    }
    
    public CategoryNotFoundException(Long id) {
        super("Category not found with id: " + id);
    }
    
    public CategoryNotFoundException(String field, String value) {
        super("Category not found with " + field + ": " + value);
    }
}

