package com.supasoft.pricingservice.dto.response;

import com.supasoft.common.dto.BaseResponse;
import com.supasoft.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for item price response
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse extends BaseResponse {
    
    private Long itemId;
    
    private Long priceTypeId;
    
    private String priceTypeCode;
    
    private String priceTypeName;
    
    private BigDecimal sellingPrice;
    
    private BigDecimal costPrice;
    
    private BigDecimal profitMargin;
    
    private BigDecimal profitAmount;
    
    private BigDecimal minPrice;
    
    private BigDecimal maxPrice;
    
    private LocalDateTime effectiveDate;
    
    private LocalDateTime expiryDate;
    
    private Status status;
    
    private Boolean isTaxable;
    
    private BigDecimal taxRate;
    
    private BigDecimal priceWithTax;
}

