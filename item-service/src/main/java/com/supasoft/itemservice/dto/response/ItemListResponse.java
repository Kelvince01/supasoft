package com.supasoft.itemservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Optimized DTO for item list views (minimal fields for performance)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemListResponse {
    
    private Long itemId;
    private String itemCode;
    private String barcode;
    private String itemName;
    private String imageUrl;
    private String categoryName;
    private String brandName;
    private BigDecimal sellingPrice;
    private String baseUomSymbol;
    private Boolean isActive;
    private Boolean isForSale;
    private LocalDateTime createdAt;
}

