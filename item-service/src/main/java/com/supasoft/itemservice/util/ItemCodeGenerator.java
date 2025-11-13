package com.supasoft.itemservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

/**
 * Utility class for generating unique item codes
 */
@Component
public class ItemCodeGenerator {
    
    private static final AtomicLong counter = new AtomicLong(1);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
    
    /**
     * Generate item code with format: ITEM-YYMMDD-SEQUENCE
     * Example: ITEM-240115-00001
     */
    public String generateItemCode() {
        String datePart = LocalDateTime.now().format(formatter);
        long sequence = counter.getAndIncrement();
        return String.format("ITEM-%s-%05d", datePart, sequence);
    }
    
    /**
     * Generate item code with category prefix
     * Example: CAT001-240115-00001
     */
    public String generateItemCodeWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return generateItemCode();
        }
        String datePart = LocalDateTime.now().format(formatter);
        long sequence = counter.getAndIncrement();
        return String.format("%s-%s-%05d", prefix, datePart, sequence);
    }
    
    /**
     * Generate item code with category and brand
     * Example: CAT001-BRD001-00001
     */
    public String generateItemCode(String categoryCode, String brandCode) {
        long sequence = counter.getAndIncrement();
        if (categoryCode != null && brandCode != null) {
            return String.format("%s-%s-%05d", categoryCode, brandCode, sequence);
        } else if (categoryCode != null) {
            return String.format("%s-%05d", categoryCode, sequence);
        } else {
            return String.format("ITEM-%05d", sequence);
        }
    }
    
    /**
     * Generate simple sequential item code
     * Example: ITEM-00001, ITEM-00002, etc.
     */
    public String generateSequentialCode() {
        long sequence = counter.getAndIncrement();
        return String.format("ITEM-%05d", sequence);
    }
    
    /**
     * Reset counter (use with caution, mainly for testing)
     */
    public void resetCounter() {
        counter.set(1);
    }
}

