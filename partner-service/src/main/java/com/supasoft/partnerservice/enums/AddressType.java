package com.supasoft.partnerservice.enums;

/**
 * Enum for address types
 */
public enum AddressType {
    BILLING("Billing Address"),
    SHIPPING("Shipping Address"),
    PHYSICAL("Physical Address");

    private final String description;

    AddressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

