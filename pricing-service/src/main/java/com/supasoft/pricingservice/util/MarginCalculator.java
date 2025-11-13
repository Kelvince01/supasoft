package com.supasoft.pricingservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for profit margin calculations
 */
@Slf4j
@Component
public class MarginCalculator {
    
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    
    /**
     * Calculate profit amount (Selling Price - Cost Price)
     */
    public BigDecimal calculateProfitAmount(BigDecimal sellingPrice, BigDecimal costPrice) {
        if (sellingPrice == null || costPrice == null) {
            return BigDecimal.ZERO;
        }
        
        return sellingPrice.subtract(costPrice).setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate profit margin percentage
     * Margin = (Profit / Cost Price) * 100
     */
    public BigDecimal calculateProfitMargin(BigDecimal sellingPrice, BigDecimal costPrice) {
        if (sellingPrice == null || costPrice == null || costPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal profit = calculateProfitAmount(sellingPrice, costPrice);
        
        return profit.divide(costPrice, 4, ROUNDING_MODE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate markup percentage
     * Markup = (Profit / Selling Price) * 100
     */
    public BigDecimal calculateMarkup(BigDecimal sellingPrice, BigDecimal costPrice) {
        if (sellingPrice == null || costPrice == null || sellingPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal profit = calculateProfitAmount(sellingPrice, costPrice);
        
        return profit.divide(sellingPrice, 4, ROUNDING_MODE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate selling price from cost price and desired margin
     * Selling Price = Cost Price * (1 + Margin/100)
     */
    public BigDecimal calculateSellingPriceFromMargin(BigDecimal costPrice, BigDecimal marginPercentage) {
        if (costPrice == null || marginPercentage == null) {
            return costPrice;
        }
        
        BigDecimal multiplier = BigDecimal.ONE.add(
            marginPercentage.divide(BigDecimal.valueOf(100), 4, ROUNDING_MODE)
        );
        
        return costPrice.multiply(multiplier).setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate cost price from selling price and margin
     * Cost Price = Selling Price / (1 + Margin/100)
     */
    public BigDecimal calculateCostPriceFromMargin(BigDecimal sellingPrice, BigDecimal marginPercentage) {
        if (sellingPrice == null || marginPercentage == null) {
            return sellingPrice;
        }
        
        BigDecimal divisor = BigDecimal.ONE.add(
            marginPercentage.divide(BigDecimal.valueOf(100), 4, ROUNDING_MODE)
        );
        
        return sellingPrice.divide(divisor, SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate break-even price (minimum selling price to cover costs)
     */
    public BigDecimal calculateBreakEvenPrice(BigDecimal costPrice) {
        if (costPrice == null) {
            return BigDecimal.ZERO;
        }
        
        return costPrice.setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate target price to achieve desired margin
     */
    public BigDecimal calculateTargetPrice(BigDecimal costPrice, BigDecimal targetMarginPercentage) {
        return calculateSellingPriceFromMargin(costPrice, targetMarginPercentage);
    }
    
    /**
     * Calculate margin percentage from profit amount
     * Margin = (Profit / Cost Price) * 100
     */
    public BigDecimal calculateMarginFromProfit(BigDecimal profitAmount, BigDecimal costPrice) {
        if (profitAmount == null || costPrice == null || costPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return profitAmount.divide(costPrice, 4, ROUNDING_MODE)
                .multiply(BigDecimal.valueOf(100))
                .setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Check if margin meets minimum threshold
     */
    public boolean meetsMinimumMargin(BigDecimal sellingPrice, BigDecimal costPrice, BigDecimal minimumMarginPercentage) {
        if (minimumMarginPercentage == null) {
            return true;
        }
        
        BigDecimal actualMargin = calculateProfitMargin(sellingPrice, costPrice);
        
        return actualMargin.compareTo(minimumMarginPercentage) >= 0;
    }
    
    /**
     * Calculate volume discount impact on margin
     */
    public BigDecimal calculateMarginWithDiscount(BigDecimal sellingPrice, BigDecimal costPrice, BigDecimal discountPercentage) {
        if (discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) == 0) {
            return calculateProfitMargin(sellingPrice, costPrice);
        }
        
        BigDecimal discountedPrice = sellingPrice.multiply(
            BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 4, ROUNDING_MODE))
        );
        
        return calculateProfitMargin(discountedPrice, costPrice);
    }
    
    /**
     * Calculate ROI (Return on Investment)
     * ROI = (Profit / Cost Price) * 100
     */
    public BigDecimal calculateROI(BigDecimal sellingPrice, BigDecimal costPrice) {
        return calculateProfitMargin(sellingPrice, costPrice);
    }
}

