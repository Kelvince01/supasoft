package com.supasoft.pricingservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for price calculation request
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculationRequest {
    
    private Long customerId;
    
    @NotNull(message = "Price type ID is required")
    private Long priceTypeId;
    
    private String discountCode;
    
    private String promotionCode;
    
    @NotNull(message = "Items are required")
    private List<ItemCalculation> items = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemCalculation {
        @NotNull(message = "Item ID is required")
        private Long itemId;
        
        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
        private BigDecimal quantity;
        
        private Long uomId;
    }
}

