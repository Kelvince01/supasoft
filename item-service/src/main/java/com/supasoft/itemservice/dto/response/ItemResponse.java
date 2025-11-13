package com.supasoft.itemservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic DTO for item response (for list views)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponse {
    
    private Long itemId;
    private String itemCode;
    private String barcode;
    private String itemName;
    private String shortDescription;
    private String imageUrl;
    
    // Category and Brand
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    
    // Base UOM
    private Long baseUomId;
    private String baseUomName;
    private String baseUomSymbol;
    
    // Pricing
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal vatRate;
    
    // Status
    private Boolean isActive;
    private Boolean isForSale;
    private Boolean isForPurchase;
    private Boolean trackInventory;
    private Boolean hasExpiry;
    
    // Reorder Information
    private BigDecimal reorderLevel;
    private BigDecimal minimumStockLevel;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

