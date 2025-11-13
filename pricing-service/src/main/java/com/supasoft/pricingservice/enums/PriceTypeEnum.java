package com.supasoft.pricingservice.enums;

/**
 * Enum for common Price Types
 */
public enum PriceTypeEnum {
    RETAIL("Retail Price"),
    WHOLESALE("Wholesale Price"),
    DISTRIBUTOR("Distributor Price"),
    ONLINE("Online Price"),
    MEMBER("Member Price"),
    VIP("VIP Price");
    
    private final String description;
    
    PriceTypeEnum(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}

