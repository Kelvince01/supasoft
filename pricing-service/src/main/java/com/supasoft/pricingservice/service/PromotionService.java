
package com.supasoft.pricingservice.service;

import com.supasoft.pricingservice.dto.request.CreatePromotionRequest;
import com.supasoft.pricingservice.dto.response.PromotionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for promotion operations
 */
public interface PromotionService {
    
    PromotionResponse createPromotion(CreatePromotionRequest request);
    
    PromotionResponse updatePromotion(Long id, CreatePromotionRequest request);
    
    PromotionResponse getPromotionById(Long id);
    
    PromotionResponse getPromotionByCode(String code);
    
    List<PromotionResponse> getActivePromotions();
    
    List<PromotionResponse> getActivePromotionsForItem(Long itemId);
    
    Page<PromotionResponse> getAllPromotions(Pageable pageable);
    
    void deletePromotion(Long id);
    
    void incrementUsageCount(String promotionCode);
    
    boolean validatePromotion(String promotionCode);
}
