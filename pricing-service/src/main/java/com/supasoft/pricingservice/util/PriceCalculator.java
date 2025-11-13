package com.supasoft.pricingservice.util;

import com.supasoft.pricingservice.entity.Discount;
import com.supasoft.pricingservice.entity.Promotion;
import com.supasoft.pricingservice.enums.DiscountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for price calculations
 */
@Slf4j
@Component
public class PriceCalculator {
    
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    
    /**
     * Calculate price with tax
     */
    public BigDecimal calculatePriceWithTax(BigDecimal price, BigDecimal taxRate) {
        if (price == null || taxRate == null) {
            return price;
        }
        
        BigDecimal taxMultiplier = BigDecimal.ONE.add(
            taxRate.divide(BigDecimal.valueOf(100), 4, ROUNDING_MODE)
        );
        
        return price.multiply(taxMultiplier).setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate discount amount
     */
    public BigDecimal calculateDiscountAmount(BigDecimal price, Discount discount) {
        if (price == null || discount == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discountAmount;
        
        if (discount.getDiscountType() == DiscountType.PERCENTAGE) {
            discountAmount = price.multiply(discount.getDiscountValue())
                    .divide(BigDecimal.valueOf(100), SCALE, ROUNDING_MODE);
            
            // Apply max discount limit if set
            if (discount.getMaxDiscountAmount() != null && 
                discountAmount.compareTo(discount.getMaxDiscountAmount()) > 0) {
                discountAmount = discount.getMaxDiscountAmount();
            }
        } else {
            // Fixed amount discount
            discountAmount = discount.getDiscountValue();
            
            // Discount cannot exceed price
            if (discountAmount.compareTo(price) > 0) {
                discountAmount = price;
            }
        }
        
        return discountAmount.setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate promotion discount amount
     */
    public BigDecimal calculatePromotionAmount(BigDecimal price, Promotion promotion) {
        if (price == null || promotion == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal promotionAmount = BigDecimal.ZERO;
        
        if (promotion.getDiscountPercentage() != null) {
            promotionAmount = price.multiply(promotion.getDiscountPercentage())
                    .divide(BigDecimal.valueOf(100), SCALE, ROUNDING_MODE);
        } else if (promotion.getDiscountAmount() != null) {
            promotionAmount = promotion.getDiscountAmount();
        }
        
        // Apply max discount limit if set
        if (promotion.getMaxDiscountAmount() != null && 
            promotionAmount.compareTo(promotion.getMaxDiscountAmount()) > 0) {
            promotionAmount = promotion.getMaxDiscountAmount();
        }
        
        // Promotion cannot exceed price
        if (promotionAmount.compareTo(price) > 0) {
            promotionAmount = price;
        }
        
        return promotionAmount.setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate line total (quantity * unit price)
     */
    public BigDecimal calculateLineTotal(BigDecimal quantity, BigDecimal unitPrice) {
        if (quantity == null || unitPrice == null) {
            return BigDecimal.ZERO;
        }
        
        return quantity.multiply(unitPrice).setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate tax amount
     */
    public BigDecimal calculateTaxAmount(BigDecimal price, BigDecimal taxRate) {
        if (price == null || taxRate == null) {
            return BigDecimal.ZERO;
        }
        
        return price.multiply(taxRate)
                .divide(BigDecimal.valueOf(100), SCALE, ROUNDING_MODE);
    }
    
    /**
     * Apply percentage discount to price
     */
    public BigDecimal applyPercentageDiscount(BigDecimal price, BigDecimal percentage) {
        if (price == null || percentage == null) {
            return price;
        }
        
        BigDecimal discountAmount = price.multiply(percentage)
                .divide(BigDecimal.valueOf(100), SCALE, ROUNDING_MODE);
        
        return price.subtract(discountAmount).setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Apply fixed discount to price
     */
    public BigDecimal applyFixedDiscount(BigDecimal price, BigDecimal discountAmount) {
        if (price == null || discountAmount == null) {
            return price;
        }
        
        BigDecimal result = price.subtract(discountAmount);
        
        // Price cannot be negative
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        
        return result.setScale(SCALE, ROUNDING_MODE);
    }
    
    /**
     * Calculate cumulative discounts
     * Each discount is applied to the already discounted price
     */
    public BigDecimal applyCumulativeDiscounts(BigDecimal originalPrice, BigDecimal... discountPercentages) {
        BigDecimal currentPrice = originalPrice;
        
        for (BigDecimal percentage : discountPercentages) {
            if (percentage != null) {
                currentPrice = applyPercentageDiscount(currentPrice, percentage);
            }
        }
        
        return currentPrice;
    }
    
    /**
     * Check if discount is applicable based on purchase amount
     */
    public boolean isDiscountApplicable(Discount discount, BigDecimal purchaseAmount) {
        if (discount == null || purchaseAmount == null) {
            return false;
        }
        
        // Check minimum purchase amount
        if (discount.getMinPurchaseAmount() != null && 
            purchaseAmount.compareTo(discount.getMinPurchaseAmount()) < 0) {
            return false;
        }
        
        // Check maximum purchase amount
        if (discount.getMaxPurchaseAmount() != null && 
            purchaseAmount.compareTo(discount.getMaxPurchaseAmount()) > 0) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Check if promotion is applicable based on purchase amount
     */
    public boolean isPromotionApplicable(Promotion promotion, BigDecimal purchaseAmount) {
        if (promotion == null || purchaseAmount == null) {
            return false;
        }
        
        // Check minimum purchase amount
        if (promotion.getMinPurchaseAmount() != null && 
            purchaseAmount.compareTo(promotion.getMinPurchaseAmount()) < 0) {
            return false;
        }
        
        return true;
    }
}

