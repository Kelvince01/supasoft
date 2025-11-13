package com.supasoft.itemservice.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for brand response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandResponse {
    
    private Long brandId;
    private String brandCode;
    private String brandName;
    private String description;
    private String manufacturer;
    private String countryOfOrigin;
    private String logoUrl;
    private String website;
    private Boolean isActive;
    private Long itemCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

