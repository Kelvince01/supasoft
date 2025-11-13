package com.supasoft.pricingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for price calculation response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceCalculationResponse {
    
    private BigDecimal subtotal;
    
    private BigDecimal discountAmount;
    
    private BigDecimal promotionAmount;
    
    private BigDecimal taxAmount;
    
    private BigDecimal totalAmount;
    
    private String discountCode;
    
    private String promotionCode;
    
    private List<ItemPriceDetail> items = new ArrayList<>();
    
    private List<AppliedDiscount> appliedDiscounts = new ArrayList<>();
    
    private List<AppliedPromotion> appliedPromotions = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemPriceDetail {
        private Long itemId;
        
        private BigDecimal quantity;
        
        private BigDecimal unitPrice;
        
        private BigDecimal originalPrice;
        
        private BigDecimal discountedPrice;
        
        private BigDecimal lineTotal;
        
        private BigDecimal taxAmount;
        
        private BigDecimal lineDiscount;
        
        private BigDecimal linePromotion;
        
        private String priceSource; // REGULAR, CUSTOMER_SPECIFIC, PROMOTION
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AppliedDiscount {
        private String code;
        
        private String name;
        
        private BigDecimal amount;
        
        private String applicationType; // PERCENTAGE, FIXED_AMOUNT
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AppliedPromotion {
        private String code;
        
        private String name;
        
        private BigDecimal amount;
        
        private String promotionType;
    }
}

