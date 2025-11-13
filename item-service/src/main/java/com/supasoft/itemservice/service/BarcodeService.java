package com.supasoft.itemservice.service;

/**
 * Service interface for Barcode operations
 */
public interface BarcodeService {
    
    /**
     * Generate a unique barcode
     */
    String generateUniqueBarcode(String type);
    
    /**
     * Generate barcode for a specific item
     */
    String generateBarcodeForItem(Long itemId, String type);
    
    /**
     * Validate barcode format
     */
    boolean validateBarcode(String barcode, String type);
    
    /**
     * Check if barcode is unique
     */
    boolean isBarcodeUnique(String barcode);
    
    /**
     * Add barcode to item
     */
    void addBarcodeToItem(Long itemId, String barcode, String barcodeType, boolean isPrimary);
}

