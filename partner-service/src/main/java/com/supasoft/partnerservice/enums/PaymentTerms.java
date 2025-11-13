package com.supasoft.partnerservice.enums;

/**
 * Enum for payment terms
 */
public enum PaymentTerms {
    COD("Cash on Delivery", 0),
    NET7("Net 7 Days", 7),
    NET15("Net 15 Days", 15),
    NET30("Net 30 Days", 30),
    NET60("Net 60 Days", 60),
    NET90("Net 90 Days", 90),
    ADVANCE("Advance Payment", 0);

    private final String description;
    private final int days;

    PaymentTerms(String description, int days) {
        this.description = description;
        this.days = days;
    }

    public String getDescription() {
        return description;
    }

    public int getDays() {
        return days;
    }
}

