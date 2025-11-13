package com.supasoft.itemservice.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.supasoft.itemservice.dto.request.CreateItemRequest;
import com.supasoft.itemservice.dto.request.UpdateItemRequest;
import com.supasoft.itemservice.dto.response.ItemBarcodeResponse;
import com.supasoft.itemservice.dto.response.ItemDetailResponse;
import com.supasoft.itemservice.dto.response.ItemListResponse;
import com.supasoft.itemservice.dto.response.ItemResponse;
import com.supasoft.itemservice.dto.response.ItemUomConversionResponse;
import com.supasoft.itemservice.entity.Item;
import com.supasoft.itemservice.entity.ItemBarcode;
import com.supasoft.itemservice.entity.ItemUomConversion;

/**
 * MapStruct mapper for Item entity
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, BrandMapper.class, UomMapper.class})
public interface ItemMapper {
    
    /**
     * Convert Item entity to ItemResponse (basic)
     */
    @Mapping(source = "category.categoryId", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "brand.brandId", target = "brandId")
    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "baseUom.uomId", target = "baseUomId")
    @Mapping(source = "baseUom.uomName", target = "baseUomName")
    @Mapping(source = "baseUom.symbol", target = "baseUomSymbol")
    ItemResponse toResponse(Item item);
    
    /**
     * Convert Item entity to ItemDetailResponse (detailed)
     */
    @Mapping(source = "supplier.supplierId", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierName")
    @Mapping(target = "profitMargin", expression = "java(calculateProfitMargin(item))")
    @Mapping(target = "profitPercentage", expression = "java(calculateProfitPercentage(item))")
    @Mapping(source = "barcodes", target = "barcodes", qualifiedByName = "toItemBarcodeResponseList")
    @Mapping(source = "uomConversions", target = "uomConversions", qualifiedByName = "toItemUomConversionResponseList")
    ItemDetailResponse toDetailResponse(Item item);
    
    /**
     * Convert Item entity to ItemListResponse (optimized for lists)
     */
    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "baseUom.symbol", target = "baseUomSymbol")
    ItemListResponse toListResponse(Item item);
    
    /**
     * Convert CreateItemRequest to Item entity
     */
    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "category", ignore = true) // Set by service
    @Mapping(target = "brand", ignore = true) // Set by service
    @Mapping(target = "baseUom", ignore = true) // Set by service
    @Mapping(target = "supplier", ignore = true) // Set by service
    @Mapping(target = "barcodes", ignore = true)
    @Mapping(target = "uomConversions", ignore = true)
    Item toEntity(CreateItemRequest request);
    
    /**
     * Update existing Item entity from UpdateItemRequest
     */
    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "itemCode", ignore = true) // Cannot change code
    @Mapping(target = "barcode", ignore = true) // Cannot change barcode via update
    @Mapping(target = "category", ignore = true) // Set by service
    @Mapping(target = "brand", ignore = true) // Set by service
    @Mapping(target = "baseUom", ignore = true) // Cannot change base UOM
    @Mapping(target = "supplier", ignore = true) // Set by service
    @Mapping(target = "barcodes", ignore = true)
    @Mapping(target = "uomConversions", ignore = true)
    @Mapping(target = "isSerialized", ignore = true) // Cannot change serialization flag
    @Mapping(target = "isBatchTracked", ignore = true) // Cannot change batch tracking flag
    void updateEntity(UpdateItemRequest request, @MappingTarget Item item);
    
    /**
     * Convert ItemBarcode entity to ItemBarcodeResponse
     */
    @Named("toItemBarcodeResponse")
    @Mapping(target = "barcodeId", source = "barcodeId")
    ItemBarcodeResponse toItemBarcodeResponse(ItemBarcode itemBarcode);
    
    /**
     * Convert list of ItemBarcode entities to list of ItemBarcodeResponse
     */
    @Named("toItemBarcodeResponseList")
    default List<ItemBarcodeResponse> toItemBarcodeResponseList(List<ItemBarcode> barcodes) {
        if (barcodes == null) return null;
        return barcodes.stream()
                .map(this::toItemBarcodeResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert ItemUomConversion entity to ItemUomConversionResponse
     */
    @Named("toItemUomConversionResponse")
    @Mapping(source = "fromUom.uomId", target = "fromUomId")
    @Mapping(source = "fromUom.uomName", target = "fromUomName")
    @Mapping(source = "fromUom.symbol", target = "fromUomSymbol")
    @Mapping(source = "toUom.uomId", target = "toUomId")
    @Mapping(source = "toUom.uomName", target = "toUomName")
    @Mapping(source = "toUom.symbol", target = "toUomSymbol")
    @Mapping(target = "conversionText", expression = "java(buildConversionText(conversion))")
    ItemUomConversionResponse toItemUomConversionResponse(ItemUomConversion conversion);
    
    /**
     * Convert list of ItemUomConversion entities to list of ItemUomConversionResponse
     */
    @Named("toItemUomConversionResponseList")
    default List<ItemUomConversionResponse> toItemUomConversionResponseList(List<ItemUomConversion> conversions) {
        if (conversions == null) return null;
        return conversions.stream()
                .map(this::toItemUomConversionResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Calculate profit margin (selling price - cost price)
     */
    default BigDecimal calculateProfitMargin(Item item) {
        if (item.getSellingPrice() == null || item.getCostPrice() == null) {
            return BigDecimal.ZERO;
        }
        return item.getSellingPrice().subtract(item.getCostPrice());
    }
    
    /**
     * Calculate profit percentage ((selling price - cost price) / cost price * 100)
     */
    default BigDecimal calculateProfitPercentage(Item item) {
        if (item.getSellingPrice() == null || item.getCostPrice() == null || 
            item.getCostPrice().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal margin = item.getSellingPrice().subtract(item.getCostPrice());
        return margin.divide(item.getCostPrice(), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }
    
    /**
     * Build conversion text (e.g., "1 carton = 24 pieces")
     */
    default String buildConversionText(ItemUomConversion conversion) {
        return String.format("1 %s = %s %s",
                conversion.getFromUom().getUomName(),
                conversion.getConversionFactor(),
                conversion.getToUom().getUomName());
    }
}

