package com.supasoft.itemservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.supasoft.itemservice.util.BarcodeGenerator;

import lombok.RequiredArgsConstructor;

/**
 * Validator for barcode format and check digit validation
 */
@Component
@RequiredArgsConstructor
public class BarcodeValidator implements ConstraintValidator<UniqueBarcode, String> {
    
    private final BarcodeGenerator barcodeGenerator;
    
    @Override
    public void initialize(UniqueBarcode constraintAnnotation) {
        // Initialization if needed
    }
    
    @Override
    public boolean isValid(String barcode, ConstraintValidatorContext context) {
        if (barcode == null || barcode.isEmpty()) {
            return true; // Null/empty handled by @NotBlank if required
        }
        
        // Check if it's a valid EAN-13
        if (barcode.length() == 13) {
            return barcodeGenerator.validateEAN13(barcode);
        }
        
        // For CODE-128, check if it's alphanumeric and within length limits
        if (barcode.length() >= 1 && barcode.length() <= 20) {
            return barcode.matches("[A-Za-z0-9]+");
        }
        
        return false;
    }
}

