package com.supasoft.partnerservice.enums;

/**
 * Enum for partner transaction types
 */
public enum PartnerTransactionType {
    SALE("Sale Transaction"),
    PAYMENT("Payment Received"),
    REFUND("Refund"),
    CREDIT_NOTE("Credit Note"),
    DEBIT_NOTE("Debit Note"),
    ADJUSTMENT("Balance Adjustment");

    private final String description;

    PartnerTransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

