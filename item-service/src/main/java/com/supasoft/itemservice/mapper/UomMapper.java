package com.supasoft.itemservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.supasoft.itemservice.dto.request.UomRequest;
import com.supasoft.itemservice.dto.response.UomResponse;
import com.supasoft.itemservice.entity.UnitOfMeasure;

/**
 * MapStruct mapper for UnitOfMeasure entity
 */
@Mapper(componentModel = "spring")
public interface UomMapper {
    
    /**
     * Convert UnitOfMeasure entity to UomResponse
     */
    UomResponse toResponse(UnitOfMeasure uom);
    
    /**
     * Convert UomRequest to UnitOfMeasure entity
     */
    @Mapping(target = "uomId", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "conversions", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    UnitOfMeasure toEntity(UomRequest request);
    
    /**
     * Update existing UnitOfMeasure entity from request
     */
    @Mapping(target = "uomId", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "conversions", ignore = true)
    void updateEntity(UomRequest request, @MappingTarget UnitOfMeasure uom);
}

