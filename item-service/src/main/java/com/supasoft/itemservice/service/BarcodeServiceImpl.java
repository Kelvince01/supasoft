package com.supasoft.itemservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supasoft.common.exception.BusinessException;
import com.supasoft.itemservice.entity.Item;
import com.supasoft.itemservice.entity.ItemBarcode;
import com.supasoft.itemservice.repository.ItemBarcodeRepository;
import com.supasoft.itemservice.repository.ItemRepository;
import com.supasoft.itemservice.util.BarcodeGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Barcode operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BarcodeServiceImpl implements BarcodeService {
    
    private final ItemBarcodeRepository barcodeRepository;
    private final ItemRepository itemRepository;
    private final BarcodeGenerator barcodeGenerator;
    
    @Override
    public String generateUniqueBarcode(String type) {
        String barcode;
        int attempts = 0;
        int maxAttempts = 10;
        
        do {
            barcode = "EAN13".equalsIgnoreCase(type) 
                    ? barcodeGenerator.generateEAN13() 
                    : barcodeGenerator.generateCODE128(12);
            attempts++;
            
            if (attempts >= maxAttempts) {
                throw new BusinessException("Unable to generate unique barcode after " + maxAttempts + " attempts");
            }
        } while (barcodeRepository.existsByBarcode(barcode) || itemRepository.existsByBarcode(barcode));
        
        log.info("Generated unique barcode: {}", barcode);
        return barcode;
    }
    
    @Override
    public String generateBarcodeForItem(Long itemId, String type) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException("Item not found with ID: " + itemId));
        
        String barcode = barcodeGenerator.generateFromItemCode(item.getItemCode(), type);
        
        // Ensure uniqueness
        int attempts = 0;
        while ((barcodeRepository.existsByBarcode(barcode) || itemRepository.existsByBarcode(barcode)) && attempts < 10) {
            barcode = generateUniqueBarcode(type);
            attempts++;
        }
        
        log.info("Generated barcode for item {}: {}", itemId, barcode);
        return barcode;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean validateBarcode(String barcode, String type) {
        if ("EAN13".equalsIgnoreCase(type)) {
            return barcodeGenerator.validateEAN13(barcode);
        }
        // For CODE128, just check format
        return barcode != null && barcode.length() >= 1 && barcode.length() <= 20;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isBarcodeUnique(String barcode) {
        return !barcodeRepository.existsByBarcode(barcode) && !itemRepository.existsByBarcode(barcode);
    }
    
    @Override
    public void addBarcodeToItem(Long itemId, String barcode, String barcodeType, boolean isPrimary) {
        log.info("Adding barcode {} to item {}", barcode, itemId);
        
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException("Item not found with ID: " + itemId));
        
        // Check if barcode already exists
        if (barcodeRepository.existsByBarcode(barcode) || itemRepository.existsByBarcode(barcode)) {
            throw new BusinessException("Barcode already exists: " + barcode);
        }
        
        // If setting as primary, unset other primary barcodes
        if (isPrimary) {
            barcodeRepository.findByItemId(itemId).forEach(existing -> {
                if (existing.getIsPrimary()) {
                    existing.setIsPrimary(false);
                    barcodeRepository.save(existing);
                }
            });
        }
        
        ItemBarcode itemBarcode = ItemBarcode.builder()
                .item(item)
                .barcode(barcode)
                .barcodeType(barcodeType)
                .isPrimary(isPrimary)
                .isActive(true)
                .build();
        
        barcodeRepository.save(itemBarcode);
        log.info("Barcode added successfully to item {}", itemId);
    }
}

