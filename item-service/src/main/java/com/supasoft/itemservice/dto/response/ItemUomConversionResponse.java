package com.supasoft.itemservice.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for item UOM conversion response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemUomConversionResponse {
    
    private Long conversionId;
    private Long fromUomId;
    private String fromUomName;
    private String fromUomSymbol;
    private Long toUomId;
    private String toUomName;
    private String toUomSymbol;
    private BigDecimal conversionFactor;
    private String barcode;
    private Boolean isActive;
    private String conversionText; // e.g., "1 carton = 24 pieces"
}

