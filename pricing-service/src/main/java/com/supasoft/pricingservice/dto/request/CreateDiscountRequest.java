package com.supasoft.pricingservice.dto.request;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.enums.DiscountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for creating discount
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiscountRequest {
    
    @NotBlank(message = "Discount code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    private String code;
    
    @NotBlank(message = "Discount name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Discount type is required")
    private DiscountType discountType;
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.00", message = "Discount value must be non-negative")
    private BigDecimal discountValue;
    
    private BigDecimal maxDiscountAmount;
    
    private BigDecimal minPurchaseAmount;
    
    private BigDecimal maxPurchaseAmount;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    private Integer usageLimit;
    
    private Integer perCustomerLimit;
    
    private Boolean isCumulative;
    
    private Boolean appliesToItems;
    
    private Boolean appliesToCategories;
    
    private List<Long> itemIds;
    
    private List<Long> categoryIds;
    
    private Status status;
}

