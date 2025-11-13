package com.supasoft.itemservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.supasoft.itemservice.dto.request.CreateCategoryRequest;
import com.supasoft.itemservice.dto.response.CategoryResponse;
import com.supasoft.itemservice.entity.Category;

/**
 * MapStruct mapper for Category entity
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    /**
     * Convert Category entity to CategoryResponse
     */
    @Mapping(source = "parent.categoryId", target = "parentCategoryId")
    @Mapping(source = "parent.categoryName", target = "parentCategoryName")
    @Mapping(target = "fullPath", expression = "java(category.getFullPath())")
    @Mapping(target = "itemCount", ignore = true) // Set by service
    @Mapping(target = "childCount", ignore = true) // Set by service
    @Mapping(target = "children", ignore = true) // Set by service if needed
    CategoryResponse toResponse(Category category);
    
    /**
     * Convert CreateCategoryRequest to Category entity
     */
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "parent", ignore = true) // Set by service
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "level", ignore = true) // Set by service
    @Mapping(target = "isActive", constant = "true")
    Category toEntity(CreateCategoryRequest request);
    
    /**
     * Update existing Category entity from request
     */
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "level", ignore = true)
    void updateEntity(CreateCategoryRequest request, @MappingTarget Category category);
}

