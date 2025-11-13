package com.supasoft.itemservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating/updating Unit of Measure
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UomRequest {
    
    @NotBlank(message = "UOM code is required")
    @Size(max = 20, message = "UOM code must not exceed 20 characters")
    private String uomCode;
    
    @NotBlank(message = "UOM name is required")
    @Size(max = 50, message = "UOM name must not exceed 50 characters")
    private String uomName;
    
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
    
    @Size(max = 20, message = "UOM type must not exceed 20 characters")
    private String uomType; // WEIGHT, VOLUME, COUNT, LENGTH
    
    @Size(max = 10, message = "Symbol must not exceed 10 characters")
    private String symbol;
    
    private Boolean isBaseUnit = false;
}

