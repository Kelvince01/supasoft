package com.supasoft.pricingservice.dto.response;

import com.supasoft.common.dto.BaseResponse;
import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for discount response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse extends BaseResponse {
    
    private String code;
    
    private String name;
    
    private String description;
    
    private DiscountType discountType;
    
    private BigDecimal discountValue;
    
    private BigDecimal maxDiscountAmount;
    
    private BigDecimal minPurchaseAmount;
    
    private BigDecimal maxPurchaseAmount;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer usageLimit;
    
    private Integer usageCount;
    
    private Integer perCustomerLimit;
    
    private Boolean isCumulative;
    
    private Boolean appliesToItems;
    
    private Boolean appliesToCategories;
    
    private List<Long> itemIds;
    
    private List<Long> categoryIds;
    
    private Status status;
    
    private Boolean isActive;
}

