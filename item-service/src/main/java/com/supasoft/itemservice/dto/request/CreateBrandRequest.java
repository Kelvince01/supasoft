package com.supasoft.itemservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new brand
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBrandRequest {
    
    @NotBlank(message = "Brand code is required")
    @Size(max = 50, message = "Brand code must not exceed 50 characters")
    private String brandCode;
    
    @NotBlank(message = "Brand name is required")
    @Size(max = 100, message = "Brand name must not exceed 100 characters")
    private String brandName;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    @Size(max = 100, message = "Manufacturer must not exceed 100 characters")
    private String manufacturer;
    
    @Size(max = 50, message = "Country of origin must not exceed 50 characters")
    private String countryOfOrigin;
    
    private String logoUrl;
    
    private String website;
}

