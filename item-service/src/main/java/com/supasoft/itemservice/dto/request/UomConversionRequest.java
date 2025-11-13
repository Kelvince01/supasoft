package com.supasoft.itemservice.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating/updating UOM conversion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UomConversionRequest {
    
    @NotNull(message = "Item ID is required")
    private Long itemId;
    
    @NotNull(message = "From UOM is required")
    private Long fromUomId;
    
    @NotNull(message = "To UOM is required")
    private Long toUomId;
    
    @NotNull(message = "Conversion factor is required")
    @DecimalMin(value = "0.0001", message = "Conversion factor must be greater than 0")
    private BigDecimal conversionFactor;
    
    private String barcode;
}

