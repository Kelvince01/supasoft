package com.supasoft.pricingservice.mapper;

import com.supasoft.pricingservice.dto.request.CreateDiscountRequest;
import com.supasoft.pricingservice.dto.request.CreatePriceRequest;
import com.supasoft.pricingservice.dto.request.UpdatePriceRequest;
import com.supasoft.pricingservice.dto.response.DiscountResponse;
import com.supasoft.pricingservice.dto.response.PriceResponse;
import com.supasoft.pricingservice.entity.Discount;
import com.supasoft.pricingservice.entity.ItemPrice;
import com.supasoft.pricingservice.entity.PriceType;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MapStruct mapper for Pricing entities
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PricingMapper {
    
    // ItemPrice mappings
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "priceType", ignore = true)
    @Mapping(target = "profitMargin", ignore = true)
    @Mapping(target = "profitAmount", ignore = true)
    ItemPrice toEntity(CreatePriceRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "priceType", ignore = true)
    @Mapping(target = "profitMargin", ignore = true)
    @Mapping(target = "profitAmount", ignore = true)
    void updateEntity(UpdatePriceRequest request, @MappingTarget ItemPrice itemPrice);
    
    @Mapping(target = "priceTypeId", source = "priceType.id")
    @Mapping(target = "priceTypeCode", source = "priceType.code")
    @Mapping(target = "priceTypeName", source = "priceType.name")
    @Mapping(target = "priceWithTax", expression = "java(calculatePriceWithTax(itemPrice))")
    PriceResponse toResponse(ItemPrice itemPrice);
    
    default java.math.BigDecimal calculatePriceWithTax(ItemPrice itemPrice) {
        if (itemPrice.getIsTaxable() && itemPrice.getTaxRate() != null) {
            java.math.BigDecimal taxMultiplier = java.math.BigDecimal.ONE.add(
                itemPrice.getTaxRate().divide(java.math.BigDecimal.valueOf(100))
            );
            return itemPrice.getSellingPrice().multiply(taxMultiplier);
        }
        return itemPrice.getSellingPrice();
    }
    
    // Discount mappings
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "usageCount", constant = "0")
    @Mapping(target = "itemIds", expression = "java(listToString(request.getItemIds()))")
    @Mapping(target = "categoryIds", expression = "java(listToString(request.getCategoryIds()))")
    Discount toEntity(CreateDiscountRequest request);
    
    @Mapping(target = "itemIds", expression = "java(stringToList(discount.getItemIds()))")
    @Mapping(target = "categoryIds", expression = "java(stringToList(discount.getCategoryIds()))")
    @Mapping(target = "isActive", expression = "java(discount.isActive())")
    DiscountResponse toResponse(Discount discount);
    
    default String listToString(List<Long> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
    
    default List<Long> stringToList(String str) {
        if (str == null || str.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}

