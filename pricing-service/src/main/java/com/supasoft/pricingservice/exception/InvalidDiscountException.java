package com.supasoft.pricingservice.exception;

import com.supasoft.common.exception.BusinessException;

/**
 * Exception thrown when a discount is invalid or cannot be applied
 */
public class InvalidDiscountException extends BusinessException {
    
    public InvalidDiscountException(String message) {
        super(message);
    }
    
    public InvalidDiscountException(String discountCode, String reason) {
        super("Invalid discount '" + discountCode + "': " + reason);
    }
}

