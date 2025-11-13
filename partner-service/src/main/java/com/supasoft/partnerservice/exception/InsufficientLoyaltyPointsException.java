package com.supasoft.partnerservice.exception;

import com.supasoft.common.exception.BusinessException;

/**
 * Exception thrown when a customer has insufficient loyalty points
 */
public class InsufficientLoyaltyPointsException extends BusinessException {
    
    public InsufficientLoyaltyPointsException(Long customerId, Integer required, Integer available) {
        super(String.format("Customer %d has insufficient loyalty points. Required: %d, Available: %d", 
                customerId, required, available));
    }
    
    public InsufficientLoyaltyPointsException(String message) {
        super(message);
    }
}
