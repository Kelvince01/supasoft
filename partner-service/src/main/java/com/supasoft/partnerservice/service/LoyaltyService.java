package com.supasoft.partnerservice.service;

import com.supasoft.partnerservice.entity.LoyaltyTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service interface for Loyalty management
 * TODO: Implement full loyalty points management
 */
public interface LoyaltyService {
    
    Integer calculatePointsForPurchase(Long customerId, BigDecimal purchaseAmount);
    
    void earnPoints(Long customerId, Integer points, String reference);
    
    void redeemPoints(Long customerId, Integer points, String reference);
    
    Integer getAvailablePoints(Long customerId);
    
    Page<LoyaltyTransaction> getTransactionHistory(Long customerId, Pageable pageable);
    
    void expirePoints();
}

