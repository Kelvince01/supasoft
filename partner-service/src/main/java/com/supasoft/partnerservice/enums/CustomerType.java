package com.supasoft.partnerservice.enums;

/**
 * Enum for customer types
 */
public enum CustomerType {
    RETAIL("Retail Customer"),
    WHOLESALE("Wholesale Customer"),
    VIP("VIP Customer"),
    CORPORATE("Corporate Customer");

    private final String description;

    CustomerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

