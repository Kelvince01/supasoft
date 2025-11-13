package com.supasoft.partnerservice.exception;

import com.supasoft.common.exception.BusinessException;

import java.math.BigDecimal;

/**
 * Exception thrown when a customer has insufficient credit
 */
public class InsufficientCreditException extends BusinessException {
    
    public InsufficientCreditException(Long customerId, BigDecimal required, BigDecimal available) {
        super(String.format("Customer %d has insufficient credit. Required: %s, Available: %s", 
                customerId, required, available));
    }
    
    public InsufficientCreditException(String message) {
        super(message);
    }
}

