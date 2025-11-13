package com.supasoft.pricingservice.enums;

/**
 * Enum for Discount Types
 */
public enum DiscountType {
    PERCENTAGE("Percentage Discount"),
    FIXED_AMOUNT("Fixed Amount Discount"),
    BUY_X_GET_Y("Buy X Get Y"),
    VOLUME_DISCOUNT("Volume Discount");
    
    private final String description;
    
    DiscountType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}

