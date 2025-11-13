package com.supasoft.itemservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for category response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    
    private Long categoryId;
    private String categoryCode;
    private String categoryName;
    private String description;
    private Long parentCategoryId;
    private String parentCategoryName;
    private Integer level;
    private Integer sortOrder;
    private String imageUrl;
    private Boolean isActive;
    private String fullPath;
    private Long itemCount;
    private Long childCount;
    private List<CategoryResponse> children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

