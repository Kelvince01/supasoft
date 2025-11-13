package com.supasoft.itemservice.exception;

import com.supasoft.common.exception.BusinessException;

/**
 * Exception thrown when attempting to create or update an item with a duplicate barcode
 */
public class DuplicateBarcodeException extends BusinessException {
    
    public DuplicateBarcodeException(String barcode) {
        super("Barcode already exists: " + barcode);
    }
    
    public DuplicateBarcodeException(String message, Throwable cause) {
        super(message, cause);
    }
}

