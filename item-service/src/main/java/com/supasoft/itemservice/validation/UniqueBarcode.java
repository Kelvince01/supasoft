package com.supasoft.itemservice.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Annotation for validating barcode uniqueness and format
 */
@Documented
@Constraint(validatedBy = BarcodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueBarcode {
    
    String message() default "Invalid barcode format";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}

