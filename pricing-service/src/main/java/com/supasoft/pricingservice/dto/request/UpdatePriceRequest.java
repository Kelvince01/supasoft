package com.supasoft.pricingservice.dto.request;

import com.supasoft.common.enums.Status;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for updating item price
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePriceRequest {
    
    @DecimalMin(value = "0.01", message = "Selling price must be greater than 0")
    private BigDecimal sellingPrice;
    
    @DecimalMin(value = "0.00", message = "Cost price must be non-negative")
    private BigDecimal costPrice;
    
    @DecimalMin(value = "0.00", message = "Min price must be non-negative")
    private BigDecimal minPrice;
    
    @DecimalMin(value = "0.00", message = "Max price must be non-negative")
    private BigDecimal maxPrice;
    
    private LocalDateTime effectiveDate;
    
    private LocalDateTime expiryDate;
    
    private Status status;
    
    private Boolean isTaxable;
    
    @DecimalMin(value = "0.00", message = "Tax rate must be non-negative")
    private BigDecimal taxRate;
    
    private String changeReason;
}

