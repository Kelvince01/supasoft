package com.supasoft.pricingservice.service;

import com.supasoft.pricingservice.dto.request.PriceCalculationRequest;
import com.supasoft.pricingservice.dto.response.PriceCalculationResponse;

/**
 * Service interface for complex price calculations
 */
public interface PriceCalculationService {
    
    PriceCalculationResponse calculatePrice(PriceCalculationRequest request);
    
    PriceCalculationResponse calculatePriceWithDiscounts(
            PriceCalculationRequest request, 
            String discountCode
    );
    
    PriceCalculationResponse calculatePriceWithPromotions(
            PriceCalculationRequest request, 
            String promotionCode
    );
    
    PriceCalculationResponse calculatePriceWithAll(PriceCalculationRequest request);
}

