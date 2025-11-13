package com.supasoft.itemservice.mapper;

import com.supasoft.itemservice.dto.request.CreateBrandRequest;
import com.supasoft.itemservice.dto.response.BrandResponse;
import com.supasoft.itemservice.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for Brand entity
 */
@Mapper(componentModel = "spring")
public interface BrandMapper {

    /**
     * Convert Brand entity to BrandResponse
     */
    @Mapping(target = "itemCount", ignore = true) // Set by service
    BrandResponse toResponse(Brand brand);

    /**
     * Convert CreateBrandRequest to Brand entity
     */
    @Mapping(target = "brandId", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    Brand toEntity(CreateBrandRequest request);

    /**
     * Update existing Brand entity from request
     */
    @Mapping(target = "brandId", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateEntity(CreateBrandRequest request, @MappingTarget Brand brand);
}

