package com.supasoft.pricingservice.service;

import com.supasoft.pricingservice.dto.request.CreateDiscountRequest;
import com.supasoft.pricingservice.dto.response.DiscountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for discount operations
 */
public interface DiscountService {
    
    DiscountResponse createDiscount(CreateDiscountRequest request);
    
    DiscountResponse updateDiscount(Long id, CreateDiscountRequest request);
    
    DiscountResponse getDiscountById(Long id);
    
    DiscountResponse getDiscountByCode(String code);
    
    List<DiscountResponse> getActiveDiscounts();
    
    List<DiscountResponse> getActiveDiscountsForItem(Long itemId);
    
    Page<DiscountResponse> getAllDiscounts(Pageable pageable);
    
    void deleteDiscount(Long id);
    
    void incrementUsageCount(String discountCode);
    
    boolean validateDiscount(String discountCode);
}

