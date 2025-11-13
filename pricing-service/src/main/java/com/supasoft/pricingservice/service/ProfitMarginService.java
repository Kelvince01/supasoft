package com.supasoft.pricingservice.service;

import com.supasoft.pricingservice.dto.response.ProfitMarginResponse;

import java.math.BigDecimal;

/**
 * Service interface for profit margin calculations
 */
public interface ProfitMarginService {
    
    ProfitMarginResponse calculateMargin(Long itemId, Long priceTypeId);
    
    ProfitMarginResponse calculateMarginFromPrices(BigDecimal sellingPrice, BigDecimal costPrice);
    
    BigDecimal calculateTargetPrice(BigDecimal costPrice, BigDecimal targetMargin);
    
    boolean validateMinimumMargin(Long itemId, Long priceTypeId, BigDecimal minimumMargin);
}

