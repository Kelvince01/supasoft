package com.supasoft.pricingservice.service;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.dto.request.CreatePromotionRequest;
import com.supasoft.pricingservice.dto.response.PromotionResponse;
import com.supasoft.pricingservice.entity.Promotion;
import com.supasoft.pricingservice.entity.PromotionItem;
import com.supasoft.pricingservice.exception.PromotionExpiredException;
import com.supasoft.pricingservice.mapper.PromotionMapper;
import com.supasoft.pricingservice.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for promotion operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    
    @Override
    @Transactional
    public PromotionResponse createPromotion(CreatePromotionRequest request) {
        log.info("Creating promotion with code: {}", request.getCode());
        
        if (promotionRepository.existsByCode(request.getCode())) {
            throw new PromotionExpiredException("Promotion code already exists: " + request.getCode());
        }
        
        Promotion promotion = promotionMapper.toEntity(request);
        
        if (promotion.getStatus() == null) {
            promotion.setStatus(Status.ACTIVE);
        }
        
        // Add promotion items
        if (request.getPromotionItems() != null && !request.getPromotionItems().isEmpty()) {
            List<PromotionItem> promotionItems = promotionMapper.toPromotionItemEntities(request.getPromotionItems());
            promotionItems.forEach(promotion::addPromotionItem);
        }
        
        Promotion savedPromotion = promotionRepository.save(promotion);
        
        log.info("Promotion created successfully with ID: {}", savedPromotion.getId());
        
        return promotionMapper.toResponse(savedPromotion);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "promotions", allEntries = true)
    public PromotionResponse updatePromotion(Long id, CreatePromotionRequest request) {
        log.info("Updating promotion with ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new PromotionExpiredException("Promotion not found: " + id));
        
        if (!promotion.getCode().equals(request.getCode()) && 
            promotionRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new PromotionExpiredException("Promotion code already exists: " + request.getCode());
        }
        
        // Update fields
        promotion.setCode(request.getCode());
        promotion.setName(request.getName());
        promotion.setDescription(request.getDescription());
        promotion.setPromotionType(request.getPromotionType());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setBuyQuantity(request.getBuyQuantity());
        promotion.setGetQuantity(request.getGetQuantity());
        promotion.setGetDiscountPercentage(request.getGetDiscountPercentage());
        promotion.setBundlePrice(request.getBundlePrice());
        promotion.setDiscountAmount(request.getDiscountAmount());
        promotion.setDiscountPercentage(request.getDiscountPercentage());
        promotion.setMinPurchaseAmount(request.getMinPurchaseAmount());
        promotion.setMaxDiscountAmount(request.getMaxDiscountAmount());
        promotion.setUsageLimit(request.getUsageLimit());
        promotion.setPerCustomerLimit(request.getPerCustomerLimit());
        promotion.setPriority(request.getPriority());
        promotion.setIsCumulative(request.getIsCumulative());
        
        if (request.getStatus() != null) {
            promotion.setStatus(request.getStatus());
        }
        
        // Update promotion items
        promotion.getPromotionItems().clear();
        if (request.getPromotionItems() != null && !request.getPromotionItems().isEmpty()) {
            List<PromotionItem> promotionItems = promotionMapper.toPromotionItemEntities(request.getPromotionItems());
            promotionItems.forEach(promotion::addPromotionItem);
        }
        
        Promotion updatedPromotion = promotionRepository.save(promotion);
        
        log.info("Promotion updated successfully with ID: {}", id);
        
        return promotionMapper.toResponse(updatedPromotion);
    }
    
    @Override
    @Cacheable(value = "promotions", key = "#id")
    public PromotionResponse getPromotionById(Long id) {
        log.debug("Fetching promotion by ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new PromotionExpiredException("Promotion not found: " + id));
        
        return promotionMapper.toResponse(promotion);
    }
    
    @Override
    @Cacheable(value = "promotions", key = "#code")
    public PromotionResponse getPromotionByCode(String code) {
        log.debug("Fetching promotion by code: {}", code);
        
        Promotion promotion = promotionRepository.findByCode(code)
                .orElseThrow(() -> new PromotionExpiredException("Promotion not found: " + code));
        
        return promotionMapper.toResponse(promotion);
    }
    
    @Override
    @Cacheable(value = "activePromotions")
    public List<PromotionResponse> getActivePromotions() {
        log.debug("Fetching all active promotions");
        
        List<Promotion> promotions = promotionRepository.findActivePromotions(Status.ACTIVE, LocalDateTime.now());
        
        return promotions.stream()
                .map(promotionMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PromotionResponse> getActivePromotionsForItem(Long itemId) {
        log.debug("Fetching active promotions for item ID: {}", itemId);
        
        List<Promotion> promotions = promotionRepository.findActivePromotionsForItem(
                itemId, Status.ACTIVE, LocalDateTime.now()
        );
        
        return promotions.stream()
                .map(promotionMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<PromotionResponse> getAllPromotions(Pageable pageable) {
        log.debug("Fetching all promotions with pagination");
        
        Page<Promotion> promotions = promotionRepository.findAll(pageable);
        
        return promotions.map(promotionMapper::toResponse);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "promotions", allEntries = true)
    public void deletePromotion(Long id) {
        log.info("Deleting promotion with ID: {}", id);
        
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new PromotionExpiredException("Promotion not found: " + id));
        
        promotion.setStatus(Status.INACTIVE);
        promotionRepository.save(promotion);
        
        log.info("Promotion deleted successfully with ID: {}", id);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "promotions", key = "#promotionCode")
    public void incrementUsageCount(String promotionCode) {
        log.debug("Incrementing usage count for promotion: {}", promotionCode);
        
        Promotion promotion = promotionRepository.findByCode(promotionCode)
                .orElseThrow(() -> new PromotionExpiredException("Promotion not found: " + promotionCode));
        
        promotion.setUsageCount(promotion.getUsageCount() + 1);
        promotionRepository.save(promotion);
    }
    
    @Override
    public boolean validatePromotion(String promotionCode) {
        log.debug("Validating promotion: {}", promotionCode);
        
        Promotion promotion = promotionRepository.findByCode(promotionCode)
                .orElse(null);
        
        if (promotion == null) {
            return false;
        }
        
        return promotion.isActive();
    }
}

