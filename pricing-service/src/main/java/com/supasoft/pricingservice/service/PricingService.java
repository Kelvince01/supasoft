package com.supasoft.pricingservice.service;

import com.supasoft.pricingservice.dto.request.CreatePriceRequest;
import com.supasoft.pricingservice.dto.request.UpdatePriceRequest;
import com.supasoft.pricingservice.dto.response.PriceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for pricing operations
 */
public interface PricingService {
    
    PriceResponse createPrice(CreatePriceRequest request);
    
    PriceResponse updatePrice(Long id, UpdatePriceRequest request);
    
    PriceResponse getPriceById(Long id);
    
    List<PriceResponse> getPricesByItemId(Long itemId);
    
    PriceResponse getActivePriceByItemAndType(Long itemId, Long priceTypeId);
    
    Page<PriceResponse> getAllPrices(Pageable pageable);
    
    void deletePrice(Long id);
    
    List<PriceResponse> getLowMarginPrices(Double marginThreshold);
}

