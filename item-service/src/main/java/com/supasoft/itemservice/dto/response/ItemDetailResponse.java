package com.supasoft.itemservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detailed DTO for item response (for detail views with all fields)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDetailResponse {
    
    private Long itemId;
    private String itemCode;
    private String barcode;
    private String itemName;
    private String description;
    private String shortDescription;
    
    // Category and Brand
    private CategoryResponse category;
    private BrandResponse brand;
    
    // Base UOM
    private UomResponse baseUom;
    
    // Supplier
    private Long supplierId;
    private String supplierName;
    
    // Tax Information
    private BigDecimal vatRate;
    private Boolean isVatExempt;
    
    // Pricing
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private BigDecimal profitMargin;
    private BigDecimal profitPercentage;
    
    // Inventory Tracking
    private Boolean trackInventory;
    private Boolean hasExpiry;
    private Integer shelfLifeDays;
    private BigDecimal reorderLevel;
    private BigDecimal minimumStockLevel;
    private BigDecimal maximumStockLevel;
    
    // Physical Attributes
    private BigDecimal weight;
    private String weightUnit;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private String dimensionUnit;
    
    // Status & Flags
    private Boolean isActive;
    private Boolean isForSale;
    private Boolean isForPurchase;
    private Boolean isSerialized;
    private Boolean isBatchTracked;
    
    // Additional Information
    private String sku;
    private String manufacturerPartNumber;
    private String hsnCode;
    private String imageUrl;
    private String tags;
    private String notes;
    private LocalDate launchDate;
    private LocalDate discontinueDate;
    
    // Barcodes and UOM Conversions
    private List<ItemBarcodeResponse> barcodes;
    private List<ItemUomConversionResponse> uomConversions;
    
    // Audit Information
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

