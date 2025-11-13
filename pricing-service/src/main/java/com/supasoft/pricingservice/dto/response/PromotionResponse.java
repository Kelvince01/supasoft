package com.supasoft.pricingservice.dto.response;

import com.supasoft.common.dto.BaseResponse;
import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.enums.PromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for promotion response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PromotionResponse extends BaseResponse {
    
    private String code;
    
    private String name;
    
    private String description;
    
    private PromotionType promotionType;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer buyQuantity;
    
    private Integer getQuantity;
    
    private BigDecimal getDiscountPercentage;
    
    private BigDecimal bundlePrice;
    
    private BigDecimal discountAmount;
    
    private BigDecimal discountPercentage;
    
    private BigDecimal minPurchaseAmount;
    
    private BigDecimal maxDiscountAmount;
    
    private Integer usageLimit;
    
    private Integer usageCount;
    
    private Integer perCustomerLimit;
    
    private Integer priority;
    
    private Boolean isCumulative;
    
    private Status status;
    
    private Boolean isActive;
    
    private List<PromotionItemResponse> promotionItems = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromotionItemResponse {
        private Long id;
        
        private Long itemId;
        
        private String itemRole;
        
        private Integer requiredQuantity;
        
        private BigDecimal specialPrice;
        
        private BigDecimal discountPercentage;
        
        private Boolean isRequired;
    }
}

