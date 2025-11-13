package com.supasoft.pricingservice.mapper;

import com.supasoft.pricingservice.dto.request.CreatePromotionRequest;
import com.supasoft.pricingservice.dto.response.PromotionResponse;
import com.supasoft.pricingservice.entity.Promotion;
import com.supasoft.pricingservice.entity.PromotionItem;
import org.mapstruct.*;

import java.util.List;

/**
 * MapStruct mapper for Promotion entities
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromotionMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "usageCount", constant = "0")
    @Mapping(target = "promotionItems", ignore = true)
    Promotion toEntity(CreatePromotionRequest request);
    
    @Mapping(target = "isActive", expression = "java(promotion.isActive())")
    PromotionResponse toResponse(Promotion promotion);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    PromotionItem toPromotionItemEntity(CreatePromotionRequest.PromotionItemRequest request);
    
    List<PromotionItem> toPromotionItemEntities(List<CreatePromotionRequest.PromotionItemRequest> requests);
    
    PromotionResponse.PromotionItemResponse toPromotionItemResponse(PromotionItem promotionItem);
    
    List<PromotionResponse.PromotionItemResponse> toPromotionItemResponses(List<PromotionItem> promotionItems);
}

