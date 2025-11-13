package com.supasoft.itemservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for item barcode response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemBarcodeResponse {
    
    private Long barcodeId;
    private String barcode;
    private String barcodeType;
    private Boolean isPrimary;
    private Boolean isActive;
    private String description;
}

