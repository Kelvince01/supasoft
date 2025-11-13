package com.supasoft.pricingservice.service;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.dto.request.CreateDiscountRequest;
import com.supasoft.pricingservice.dto.response.DiscountResponse;
import com.supasoft.pricingservice.entity.Discount;
import com.supasoft.pricingservice.exception.InvalidDiscountException;
import com.supasoft.pricingservice.mapper.PricingMapper;
import com.supasoft.pricingservice.repository.DiscountRepository;
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
 * Service implementation for discount operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    
    private final DiscountRepository discountRepository;
    private final PricingMapper pricingMapper;
    
    @Override
    @Transactional
    public DiscountResponse createDiscount(CreateDiscountRequest request) {
        log.info("Creating discount with code: {}", request.getCode());
        
        if (discountRepository.existsByCode(request.getCode())) {
            throw new InvalidDiscountException("Discount code already exists: " + request.getCode());
        }
        
        Discount discount = pricingMapper.toEntity(request);
        
        if (discount.getStatus() == null) {
            discount.setStatus(Status.ACTIVE);
        }
        
        Discount savedDiscount = discountRepository.save(discount);
        
        log.info("Discount created successfully with ID: {}", savedDiscount.getId());
        
        return pricingMapper.toResponse(savedDiscount);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "discounts", allEntries = true)
    public DiscountResponse updateDiscount(Long id, CreateDiscountRequest request) {
        log.info("Updating discount with ID: {}", id);
        
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new InvalidDiscountException("Discount not found: " + id));
        
        if (!discount.getCode().equals(request.getCode()) && 
            discountRepository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new InvalidDiscountException("Discount code already exists: " + request.getCode());
        }
        
        // Update fields
        discount.setCode(request.getCode());
        discount.setName(request.getName());
        discount.setDescription(request.getDescription());
        discount.setDiscountType(request.getDiscountType());
        discount.setDiscountValue(request.getDiscountValue());
        discount.setMaxDiscountAmount(request.getMaxDiscountAmount());
        discount.setMinPurchaseAmount(request.getMinPurchaseAmount());
        discount.setMaxPurchaseAmount(request.getMaxPurchaseAmount());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setUsageLimit(request.getUsageLimit());
        discount.setPerCustomerLimit(request.getPerCustomerLimit());
        discount.setIsCumulative(request.getIsCumulative());
        discount.setAppliesToItems(request.getAppliesToItems());
        discount.setAppliesToCategories(request.getAppliesToCategories());
        discount.setItemIds(pricingMapper.listToString(request.getItemIds()));
        discount.setCategoryIds(pricingMapper.listToString(request.getCategoryIds()));
        
        if (request.getStatus() != null) {
            discount.setStatus(request.getStatus());
        }
        
        Discount updatedDiscount = discountRepository.save(discount);
        
        log.info("Discount updated successfully with ID: {}", id);
        
        return pricingMapper.toResponse(updatedDiscount);
    }
    
    @Override
    @Cacheable(value = "discounts", key = "#id")
    public DiscountResponse getDiscountById(Long id) {
        log.debug("Fetching discount by ID: {}", id);
        
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new InvalidDiscountException("Discount not found: " + id));
        
        return pricingMapper.toResponse(discount);
    }
    
    @Override
    @Cacheable(value = "discounts", key = "#code")
    public DiscountResponse getDiscountByCode(String code) {
        log.debug("Fetching discount by code: {}", code);
        
        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new InvalidDiscountException("Discount not found: " + code));
        
        return pricingMapper.toResponse(discount);
    }
    
    @Override
    @Cacheable(value = "activeDiscounts")
    public List<DiscountResponse> getActiveDiscounts() {
        log.debug("Fetching all active discounts");
        
        List<Discount> discounts = discountRepository.findActiveDiscounts(Status.ACTIVE, LocalDateTime.now());
        
        return discounts.stream()
                .map(pricingMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DiscountResponse> getActiveDiscountsForItem(Long itemId) {
        log.debug("Fetching active discounts for item ID: {}", itemId);
        
        List<Discount> discounts = discountRepository.findActiveDiscountsForItem(
                itemId, Status.ACTIVE, LocalDateTime.now()
        );
        
        return discounts.stream()
                .map(pricingMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<DiscountResponse> getAllDiscounts(Pageable pageable) {
        log.debug("Fetching all discounts with pagination");
        
        Page<Discount> discounts = discountRepository.findAll(pageable);
        
        return discounts.map(pricingMapper::toResponse);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "discounts", allEntries = true)
    public void deleteDiscount(Long id) {
        log.info("Deleting discount with ID: {}", id);
        
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new InvalidDiscountException("Discount not found: " + id));
        
        discount.setStatus(Status.INACTIVE);
        discountRepository.save(discount);
        
        log.info("Discount deleted successfully with ID: {}", id);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "discounts", key = "#discountCode")
    public void incrementUsageCount(String discountCode) {
        log.debug("Incrementing usage count for discount: {}", discountCode);
        
        Discount discount = discountRepository.findByCode(discountCode)
                .orElseThrow(() -> new InvalidDiscountException("Discount not found: " + discountCode));
        
        discount.setUsageCount(discount.getUsageCount() + 1);
        discountRepository.save(discount);
    }
    
    @Override
    public boolean validateDiscount(String discountCode) {
        log.debug("Validating discount: {}", discountCode);
        
        Discount discount = discountRepository.findByCode(discountCode)
                .orElse(null);
        
        if (discount == null) {
            return false;
        }
        
        return discount.isActive();
    }
}

