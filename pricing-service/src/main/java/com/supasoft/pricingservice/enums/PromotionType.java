package com.supasoft.pricingservice.enums;

/**
 * Enum for Promotion Types
 */
public enum PromotionType {
    PERCENTAGE_OFF("Percentage Off"),
    FIXED_AMOUNT_OFF("Fixed Amount Off"),
    BOGO("Buy One Get One"),
    BUY_X_GET_Y("Buy X Get Y"),
    BUY_X_GET_Y_PERCENT_OFF("Buy X Get Y% Off"),
    BUNDLE_PRICING("Bundle Pricing"),
    QUANTITY_DISCOUNT("Quantity Discount"),
    FREE_SHIPPING("Free Shipping"),
    FLASH_SALE("Flash Sale"),
    CLEARANCE("Clearance Sale");
    
    private final String description;
    
    PromotionType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}

