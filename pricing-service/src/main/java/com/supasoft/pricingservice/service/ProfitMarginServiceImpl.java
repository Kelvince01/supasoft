package com.supasoft.pricingservice.service;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.dto.response.ProfitMarginResponse;
import com.supasoft.pricingservice.entity.ItemPrice;
import com.supasoft.pricingservice.exception.PriceNotFoundException;
import com.supasoft.pricingservice.repository.ItemPriceRepository;
import com.supasoft.pricingservice.util.MarginCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Service implementation for profit margin calculations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfitMarginServiceImpl implements ProfitMarginService {
    
    private final ItemPriceRepository itemPriceRepository;
    private final MarginCalculator marginCalculator;
    
    @Override
    public ProfitMarginResponse calculateMargin(Long itemId, Long priceTypeId) {
        log.debug("Calculating margin for item ID: {} and price type ID: {}", itemId, priceTypeId);
        
        ItemPrice itemPrice = itemPriceRepository.findActiveItemPriceByType(
                itemId, priceTypeId, Status.ACTIVE, LocalDateTime.now()
        ).orElseThrow(() -> new PriceNotFoundException(itemId, priceTypeId));
        
        return buildMarginResponse(itemPrice);
    }
    
    @Override
    public ProfitMarginResponse calculateMarginFromPrices(BigDecimal sellingPrice, BigDecimal costPrice) {
        log.debug("Calculating margin from prices - Selling: {}, Cost: {}", sellingPrice, costPrice);
        
        BigDecimal profitAmount = marginCalculator.calculateProfitAmount(sellingPrice, costPrice);
        BigDecimal profitMargin = marginCalculator.calculateProfitMargin(sellingPrice, costPrice);
        BigDecimal markup = marginCalculator.calculateMarkup(sellingPrice, costPrice);
        BigDecimal breakEvenPrice = marginCalculator.calculateBreakEvenPrice(costPrice);
        
        return ProfitMarginResponse.builder()
                .itemId(null)
                .priceTypeId(null)
                .sellingPrice(sellingPrice)
                .costPrice(costPrice)
                .profitAmount(profitAmount)
                .profitMargin(profitMargin)
                .markup(markup)
                .breakEvenPrice(breakEvenPrice)
                .build();
    }
    
    @Override
    public BigDecimal calculateTargetPrice(BigDecimal costPrice, BigDecimal targetMargin) {
        log.debug("Calculating target price for cost: {} with margin: {}%", costPrice, targetMargin);
        
        return marginCalculator.calculateTargetPrice(costPrice, targetMargin);
    }
    
    @Override
    public boolean validateMinimumMargin(Long itemId, Long priceTypeId, BigDecimal minimumMargin) {
        log.debug("Validating minimum margin of {}% for item ID: {}", minimumMargin, itemId);
        
        ItemPrice itemPrice = itemPriceRepository.findActiveItemPriceByType(
                itemId, priceTypeId, Status.ACTIVE, LocalDateTime.now()
        ).orElseThrow(() -> new PriceNotFoundException(itemId, priceTypeId));
        
        return marginCalculator.meetsMinimumMargin(
                itemPrice.getSellingPrice(), 
                itemPrice.getCostPrice(), 
                minimumMargin
        );
    }
    
    private ProfitMarginResponse buildMarginResponse(ItemPrice itemPrice) {
        BigDecimal profitAmount = marginCalculator.calculateProfitAmount(
                itemPrice.getSellingPrice(), itemPrice.getCostPrice()
        );
        BigDecimal profitMargin = marginCalculator.calculateProfitMargin(
                itemPrice.getSellingPrice(), itemPrice.getCostPrice()
        );
        BigDecimal markup = marginCalculator.calculateMarkup(
                itemPrice.getSellingPrice(), itemPrice.getCostPrice()
        );
        BigDecimal breakEvenPrice = marginCalculator.calculateBreakEvenPrice(itemPrice.getCostPrice());
        
        // Calculate target price for 30% margin as example
        BigDecimal targetMargin = BigDecimal.valueOf(30);
        BigDecimal targetPrice = marginCalculator.calculateTargetPrice(itemPrice.getCostPrice(), targetMargin);
        
        return ProfitMarginResponse.builder()
                .itemId(itemPrice.getItemId())
                .priceTypeId(itemPrice.getPriceType().getId())
                .sellingPrice(itemPrice.getSellingPrice())
                .costPrice(itemPrice.getCostPrice())
                .profitAmount(profitAmount)
                .profitMargin(profitMargin)
                .markup(markup)
                .breakEvenPrice(breakEvenPrice)
                .targetMargin(targetMargin)
                .targetPrice(targetPrice)
                .build();
    }
}

