package com.supasoft.itemservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new item
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateItemRequest {
    
    @NotBlank(message = "Item code is required")
    @Size(max = 50, message = "Item code must not exceed 50 characters")
    private String itemCode;
    
    @Size(max = 50, message = "Barcode must not exceed 50 characters")
    private String barcode;
    
    @NotBlank(message = "Item name is required")
    @Size(max = 255, message = "Item name must not exceed 255 characters")
    private String itemName;
    
    private String description;
    
    @Size(max = 500, message = "Short description must not exceed 500 characters")
    private String shortDescription;
    
    private Long categoryId;
    
    private Long brandId;
    
    @NotNull(message = "Base UOM is required")
    private Long baseUomId;
    
    private Long supplierId;
    
    // Tax Information
    @DecimalMin(value = "0.0", message = "VAT rate must be positive")
    private BigDecimal vatRate = new BigDecimal("16.00");
    
    private Boolean isVatExempt = false;
    
    // Pricing
    @DecimalMin(value = "0.0", message = "Cost price must be positive")
    private BigDecimal costPrice = BigDecimal.ZERO;
    
    @DecimalMin(value = "0.0", message = "Selling price must be positive")
    private BigDecimal sellingPrice = BigDecimal.ZERO;
    
    // Inventory Tracking
    private Boolean trackInventory = true;
    private Boolean hasExpiry = false;
    private Integer shelfLifeDays;
    
    @DecimalMin(value = "0.0", message = "Reorder level must be positive")
    private BigDecimal reorderLevel = BigDecimal.ZERO;
    
    @DecimalMin(value = "0.0", message = "Minimum stock level must be positive")
    private BigDecimal minimumStockLevel = BigDecimal.ZERO;
    
    @DecimalMin(value = "0.0", message = "Maximum stock level must be positive")
    private BigDecimal maximumStockLevel = BigDecimal.ZERO;
    
    // Physical Attributes
    private BigDecimal weight;
    private String weightUnit;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private String dimensionUnit;
    
    // Status & Flags
    private Boolean isActive = true;
    private Boolean isForSale = true;
    private Boolean isForPurchase = true;
    private Boolean isSerialized = false;
    private Boolean isBatchTracked = false;
    
    // Additional Information
    @Size(max = 100, message = "SKU must not exceed 100 characters")
    private String sku;
    
    @Size(max = 100, message = "Manufacturer part number must not exceed 100 characters")
    private String manufacturerPartNumber;
    
    @Size(max = 20, message = "HSN code must not exceed 20 characters")
    private String hsnCode;
    
    private String imageUrl;
    private String tags;
    private String notes;
    private LocalDate launchDate;
    private LocalDate discontinueDate;
}

