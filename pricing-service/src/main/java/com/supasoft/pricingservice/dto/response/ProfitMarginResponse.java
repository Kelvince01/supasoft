package com.supasoft.pricingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for profit margin response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfitMarginResponse {
    
    private Long itemId;
    
    private Long priceTypeId;
    
    private BigDecimal sellingPrice;
    
    private BigDecimal costPrice;
    
    private BigDecimal profitAmount;
    
    private BigDecimal profitMargin; // Percentage
    
    private BigDecimal markup; // Percentage
    
    private BigDecimal breakEvenPrice;
    
    private BigDecimal targetMargin; // Desired margin
    
    private BigDecimal targetPrice; // Price to achieve target margin
}

