package com.supasoft.partnerservice.enums;

/**
 * Enum for loyalty tiers
 */
public enum LoyaltyTier {
    BRONZE("Bronze Tier", 1.0),
    SILVER("Silver Tier", 1.25),
    GOLD("Gold Tier", 1.5),
    PLATINUM("Platinum Tier", 2.0);

    private final String description;
    private final double multiplier;

    LoyaltyTier(String description, double multiplier) {
        this.description = description;
        this.multiplier = multiplier;
    }

    public String getDescription() {
        return description;
    }

    public double getMultiplier() {
        return multiplier;
    }
}

