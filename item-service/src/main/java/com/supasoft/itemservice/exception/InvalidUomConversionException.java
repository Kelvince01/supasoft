package com.supasoft.itemservice.exception;

import com.supasoft.common.exception.ValidationException;

/**
 * Exception thrown when a UOM conversion is invalid
 */
public class InvalidUomConversionException extends ValidationException {
    
    public InvalidUomConversionException(String message) {
        super(message);
    }
    
    public InvalidUomConversionException(String fromUom, String toUom) {
        super("Invalid UOM conversion from " + fromUom + " to " + toUom);
    }
    
    public InvalidUomConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}

