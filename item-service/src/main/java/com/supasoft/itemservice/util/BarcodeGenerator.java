package com.supasoft.itemservice.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for generating different types of barcodes
 */
@Component
@Slf4j
public class BarcodeGenerator {
    
    private static final SecureRandom random = new SecureRandom();
    private static final String EAN13_PREFIX = "254"; // Kenya country code
    
    /**
     * Generate a valid EAN-13 barcode with check digit
     * Format: 3-digit country code + 9-digit item code + 1 check digit
     */
    public String generateEAN13() {
        StringBuilder barcode = new StringBuilder(EAN13_PREFIX);
        
        // Generate 9 random digits
        for (int i = 0; i < 9; i++) {
            barcode.append(random.nextInt(10));
        }
        
        // Calculate and append check digit
        int checkDigit = calculateEAN13CheckDigit(barcode.toString());
        barcode.append(checkDigit);
        
        log.debug("Generated EAN-13 barcode: {}", barcode);
        return barcode.toString();
    }
    
    /**
     * Generate EAN-13 barcode with specific prefix
     * @param companyPrefix Company/manufacturer prefix (3-6 digits)
     * @param productCode Product code to fill remaining digits
     */
    public String generateEAN13WithPrefix(String companyPrefix, String productCode) {
        if (companyPrefix == null || companyPrefix.isEmpty()) {
            return generateEAN13();
        }
        
        // Ensure total length is 12 before check digit
        int remainingLength = 12 - companyPrefix.length();
        if (remainingLength <= 0) {
            throw new IllegalArgumentException("Company prefix too long. Must be less than 12 digits.");
        }
        
        StringBuilder barcode = new StringBuilder(companyPrefix);
        
        // Add product code or pad with zeros/random
        if (productCode != null && !productCode.isEmpty()) {
            String paddedProductCode = String.format("%0" + remainingLength + "d", 
                    Long.parseLong(productCode.substring(0, Math.min(productCode.length(), remainingLength))));
            barcode.append(paddedProductCode);
        } else {
            // Generate random digits for remaining length
            for (int i = 0; i < remainingLength; i++) {
                barcode.append(random.nextInt(10));
            }
        }
        
        // Calculate and append check digit
        int checkDigit = calculateEAN13CheckDigit(barcode.toString());
        barcode.append(checkDigit);
        
        log.debug("Generated EAN-13 barcode with prefix: {}", barcode);
        return barcode.toString();
    }
    
    /**
     * Calculate EAN-13 check digit using standard algorithm
     * @param barcode 12-digit barcode without check digit
     * @return check digit (0-9)
     */
    public int calculateEAN13CheckDigit(String barcode) {
        if (barcode.length() != 12) {
            throw new IllegalArgumentException("Barcode must be 12 digits for EAN-13 check digit calculation");
        }
        
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(barcode.charAt(i));
            // Multiply odd positions by 1, even positions by 3
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit;
    }
    
    /**
     * Validate EAN-13 barcode
     * @param barcode 13-digit EAN-13 barcode
     * @return true if valid, false otherwise
     */
    public boolean validateEAN13(String barcode) {
        if (barcode == null || barcode.length() != 13) {
            return false;
        }
        
        if (!barcode.matches("\\d{13}")) {
            return false;
        }
        
        try {
            String barcodeWithoutCheck = barcode.substring(0, 12);
            int calculatedCheckDigit = calculateEAN13CheckDigit(barcodeWithoutCheck);
            int providedCheckDigit = Character.getNumericValue(barcode.charAt(12));
            
            return calculatedCheckDigit == providedCheckDigit;
        } catch (Exception e) {
            log.error("Error validating EAN-13 barcode: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Generate CODE-128 compatible barcode
     * CODE-128 can encode alphanumeric characters
     * @param length Desired length (default 12)
     */
    public String generateCODE128(int length) {
        if (length < 1 || length > 20) {
            throw new IllegalArgumentException("CODE-128 length must be between 1 and 20");
        }
        
        StringBuilder barcode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            barcode.append(random.nextInt(10));
        }
        
        log.debug("Generated CODE-128 barcode: {}", barcode);
        return barcode.toString();
    }
    
    /**
     * Generate CODE-128 with alphanumeric characters
     * @param length Desired length
     * @param alphanumeric If true, includes letters; if false, only digits
     */
    public String generateCODE128Alphanumeric(int length, boolean alphanumeric) {
        if (!alphanumeric) {
            return generateCODE128(length);
        }
        
        if (length < 1 || length > 20) {
            throw new IllegalArgumentException("CODE-128 length must be between 1 and 20");
        }
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder barcode = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            barcode.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        log.debug("Generated alphanumeric CODE-128 barcode: {}", barcode);
        return barcode.toString();
    }
    
    /**
     * Generate a unique barcode based on item code
     * @param itemCode Item code to use as base
     * @param type Barcode type (EAN13 or CODE128)
     */
    public String generateFromItemCode(String itemCode, String type) {
        if (itemCode == null || itemCode.isEmpty()) {
            return "EAN13".equalsIgnoreCase(type) ? generateEAN13() : generateCODE128(12);
        }
        
        // Extract numeric part from item code
        String numericPart = itemCode.replaceAll("[^0-9]", "");
        
        if ("EAN13".equalsIgnoreCase(type)) {
            return generateEAN13WithPrefix(EAN13_PREFIX, numericPart);
        } else {
            // For CODE128, use item code as prefix
            int remainingLength = Math.max(12 - itemCode.length(), 0);
            StringBuilder barcode = new StringBuilder(itemCode);
            for (int i = 0; i < remainingLength; i++) {
                barcode.append(random.nextInt(10));
            }
            return barcode.toString().substring(0, Math.min(12, barcode.length()));
        }
    }
    
    /**
     * Generate a sequential barcode with prefix
     * @param prefix Prefix for the barcode
     * @param sequence Sequence number
     * @param totalLength Total length of barcode
     */
    public String generateSequential(String prefix, long sequence, int totalLength) {
        if (prefix == null) prefix = "";
        int sequenceLength = totalLength - prefix.length();
        if (sequenceLength <= 0) {
            throw new IllegalArgumentException("Total length must be greater than prefix length");
        }
        
        String sequenceStr = String.format("%0" + sequenceLength + "d", sequence);
        return prefix + sequenceStr;
    }
}

