package com.supasoft.pricingservice.service;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.dto.request.CreatePriceRequest;
import com.supasoft.pricingservice.dto.request.UpdatePriceRequest;
import com.supasoft.pricingservice.dto.response.PriceResponse;
import com.supasoft.pricingservice.entity.ItemPrice;
import com.supasoft.pricingservice.entity.PriceHistory;
import com.supasoft.pricingservice.entity.PriceType;
import com.supasoft.pricingservice.exception.PriceNotFoundException;
import com.supasoft.pricingservice.mapper.PricingMapper;
import com.supasoft.pricingservice.repository.ItemPriceRepository;
import com.supasoft.pricingservice.repository.PriceHistoryRepository;
import com.supasoft.pricingservice.repository.PriceTypeRepository;
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
 * Service implementation for pricing operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {
    
    private final ItemPriceRepository itemPriceRepository;
    private final PriceTypeRepository priceTypeRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final PricingMapper pricingMapper;
    
    @Override
    @Transactional
    @CacheEvict(value = "prices", key = "#request.itemId")
    public PriceResponse createPrice(CreatePriceRequest request) {
        log.info("Creating price for item ID: {}", request.getItemId());
        
        // Validate price type exists
        PriceType priceType = priceTypeRepository.findById(request.getPriceTypeId())
                .orElseThrow(() -> new PriceNotFoundException("Price type not found: " + request.getPriceTypeId()));
        
        // Check if price already exists
        if (itemPriceRepository.existsByItemIdAndPriceType(request.getItemId(), priceType)) {
            throw new IllegalArgumentException("Price already exists for this item and price type");
        }
        
        ItemPrice itemPrice = pricingMapper.toEntity(request);
        itemPrice.setPriceType(priceType);
        
        if (itemPrice.getStatus() == null) {
            itemPrice.setStatus(Status.ACTIVE);
        }
        
        ItemPrice savedPrice = itemPriceRepository.save(itemPrice);
        
        log.info("Price created successfully with ID: {}", savedPrice.getId());
        
        return pricingMapper.toResponse(savedPrice);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "prices", allEntries = true)
    public PriceResponse updatePrice(Long id, UpdatePriceRequest request) {
        log.info("Updating price with ID: {}", id);
        
        ItemPrice itemPrice = itemPriceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException(id));
        
        // Record price change in history if selling or cost price changed
        if (request.getSellingPrice() != null || request.getCostPrice() != null) {
            recordPriceHistory(itemPrice, request);
        }
        
        pricingMapper.updateEntity(request, itemPrice);
        
        ItemPrice updatedPrice = itemPriceRepository.save(itemPrice);
        
        log.info("Price updated successfully with ID: {}", id);
        
        return pricingMapper.toResponse(updatedPrice);
    }
    
    @Override
    @Cacheable(value = "prices", key = "#id")
    public PriceResponse getPriceById(Long id) {
        log.debug("Fetching price by ID: {}", id);
        
        ItemPrice itemPrice = itemPriceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException(id));
        
        return pricingMapper.toResponse(itemPrice);
    }
    
    @Override
    @Cacheable(value = "prices", key = "#itemId")
    public List<PriceResponse> getPricesByItemId(Long itemId) {
        log.debug("Fetching prices for item ID: {}", itemId);
        
        List<ItemPrice> prices = itemPriceRepository.findByItemIdAndStatus(itemId, Status.ACTIVE);
        
        return prices.stream()
                .map(pricingMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Cacheable(value = "prices", key = "#itemId + '_' + #priceTypeId")
    public PriceResponse getActivePriceByItemAndType(Long itemId, Long priceTypeId) {
        log.debug("Fetching active price for item ID: {} and price type ID: {}", itemId, priceTypeId);
        
        ItemPrice itemPrice = itemPriceRepository.findActiveItemPriceByType(
                itemId, priceTypeId, Status.ACTIVE, LocalDateTime.now()
        ).orElseThrow(() -> new PriceNotFoundException(itemId, priceTypeId));
        
        return pricingMapper.toResponse(itemPrice);
    }
    
    @Override
    public Page<PriceResponse> getAllPrices(Pageable pageable) {
        log.debug("Fetching all prices with pagination");
        
        Page<ItemPrice> prices = itemPriceRepository.findAll(pageable);
        
        return prices.map(pricingMapper::toResponse);
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "prices", allEntries = true)
    public void deletePrice(Long id) {
        log.info("Deleting price with ID: {}", id);
        
        ItemPrice itemPrice = itemPriceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException(id));
        
        itemPrice.setStatus(Status.INACTIVE);
        itemPriceRepository.save(itemPrice);
        
        log.info("Price deleted successfully with ID: {}", id);
    }
    
    @Override
    public List<PriceResponse> getLowMarginPrices(Double marginThreshold) {
        log.debug("Fetching low margin prices below threshold: {}%", marginThreshold);
        
        List<ItemPrice> prices = itemPriceRepository.findLowMarginPrices(marginThreshold, Status.ACTIVE);
        
        return prices.stream()
                .map(pricingMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    private void recordPriceHistory(ItemPrice currentPrice, UpdatePriceRequest request) {
        PriceHistory history = new PriceHistory();
        history.setItemId(currentPrice.getItemId());
        history.setPriceType(currentPrice.getPriceType());
        history.setOldSellingPrice(currentPrice.getSellingPrice());
        history.setNewSellingPrice(request.getSellingPrice() != null ? request.getSellingPrice() : currentPrice.getSellingPrice());
        history.setOldCostPrice(currentPrice.getCostPrice());
        history.setNewCostPrice(request.getCostPrice() != null ? request.getCostPrice() : currentPrice.getCostPrice());
        history.setOldProfitMargin(currentPrice.getProfitMargin());
        
        // Calculate new profit margin if prices changed
        if (request.getSellingPrice() != null && request.getCostPrice() != null) {
            java.math.BigDecimal newProfit = request.getSellingPrice().subtract(request.getCostPrice());
            history.setNewProfitMargin(newProfit.divide(request.getCostPrice(), 4, java.math.BigDecimal.ROUND_HALF_UP)
                    .multiply(java.math.BigDecimal.valueOf(100)));
        }
        
        history.setChangedAt(LocalDateTime.now());
        history.setChangedBy(com.supasoft.common.security.SecurityUtil.getCurrentUsername().orElse("SYSTEM"));
        history.setChangeReason(request.getChangeReason());
        
        priceHistoryRepository.save(history);
        
        log.info("Price history recorded for item ID: {}", currentPrice.getItemId());
    }
}

