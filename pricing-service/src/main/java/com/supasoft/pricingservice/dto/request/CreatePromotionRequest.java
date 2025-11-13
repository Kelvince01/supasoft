package com.supasoft.pricingservice.dto.request;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.enums.PromotionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for creating promotion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionRequest {
    
    @NotBlank(message = "Promotion code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    private String code;
    
    @NotBlank(message = "Promotion name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Promotion type is required")
    private PromotionType promotionType;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    private Integer buyQuantity;
    
    private Integer getQuantity;
    
    @DecimalMin(value = "0.00", message = "Get discount percentage must be non-negative")
    private BigDecimal getDiscountPercentage;
    
    private BigDecimal bundlePrice;
    
    @DecimalMin(value = "0.00", message = "Discount amount must be non-negative")
    private BigDecimal discountAmount;
    
    @DecimalMin(value = "0.00", message = "Discount percentage must be non-negative")
    private BigDecimal discountPercentage;
    
    private BigDecimal minPurchaseAmount;
    
    private BigDecimal maxDiscountAmount;
    
    private Integer usageLimit;
    
    private Integer perCustomerLimit;
    
    private Integer priority;
    
    private Boolean isCumulative;
    
    private Status status;
    
    private List<PromotionItemRequest> promotionItems = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionItemRequest {
        @NotNull(message = "Item ID is required")
        private Long itemId;
        
        @NotNull(message = "Item role is required")
        private String itemRole; // BUY, GET, BUNDLE
        
        private Integer requiredQuantity;
        
        private BigDecimal specialPrice;
        
        private BigDecimal discountPercentage;
        
        private Boolean isRequired;
    }
}

