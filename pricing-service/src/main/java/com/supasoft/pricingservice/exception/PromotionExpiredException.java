package com.supasoft.pricingservice.exception;

import com.supasoft.common.exception.BusinessException;

/**
 * Exception thrown when a promotion has expired
 */
public class PromotionExpiredException extends BusinessException {
    
    public PromotionExpiredException(String message) {
        super(message);
    }
    
    public static PromotionExpiredException forCode(String promotionCode) {
        return new PromotionExpiredException("Promotion '" + promotionCode + "' has expired or is not active");
    }
}

