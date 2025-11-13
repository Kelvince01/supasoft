package com.supasoft.common.constant;

/**
 * Constants for messages and labels
 */
public final class MessageConstants {
    
    private MessageConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    // Success Messages
    public static final String SUCCESS_CREATE = "Record created successfully";
    public static final String SUCCESS_UPDATE = "Record updated successfully";
    public static final String SUCCESS_DELETE = "Record deleted successfully";
    public static final String SUCCESS_FETCH = "Records fetched successfully";
    
    // Error Messages
    public static final String ERROR_NOT_FOUND = "Resource not found";
    public static final String ERROR_ALREADY_EXISTS = "Resource already exists";
    public static final String ERROR_INVALID_INPUT = "Invalid input provided";
    public static final String ERROR_UNAUTHORIZED = "Unauthorized access";
    public static final String ERROR_FORBIDDEN = "Access forbidden";
    public static final String ERROR_INTERNAL = "Internal server error occurred";
    
    // Authentication Messages
    public static final String AUTH_SUCCESS_LOGIN = "Login successful";
    public static final String AUTH_SUCCESS_LOGOUT = "Logout successful";
    public static final String AUTH_SUCCESS_REGISTER = "Registration successful";
    public static final String AUTH_ERROR_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String AUTH_ERROR_TOKEN_EXPIRED = "Token has expired";
    public static final String AUTH_ERROR_TOKEN_INVALID = "Invalid token";
    public static final String AUTH_ERROR_USER_EXISTS = "User already exists";
    public static final String AUTH_ERROR_USER_NOT_FOUND = "User not found";
    
    // Validation Messages
    public static final String VALIDATION_ERROR_REQUIRED = "This field is required";
    public static final String VALIDATION_ERROR_EMAIL = "Invalid email format";
    public static final String VALIDATION_ERROR_MIN_LENGTH = "Minimum length not met";
    public static final String VALIDATION_ERROR_MAX_LENGTH = "Maximum length exceeded";
    public static final String VALIDATION_ERROR_PATTERN = "Invalid format";
    public static final String VALIDATION_ERROR_POSITIVE = "Value must be positive";
    public static final String VALIDATION_ERROR_PAST_DATE = "Date must be in the past";
    public static final String VALIDATION_ERROR_FUTURE_DATE = "Date must be in the future";
    
    // Business Logic Messages
    public static final String BUSINESS_ERROR_INSUFFICIENT_STOCK = "Insufficient stock available";
    public static final String BUSINESS_ERROR_INVALID_QUANTITY = "Invalid quantity";
    public static final String BUSINESS_ERROR_INVALID_PRICE = "Invalid price";
    public static final String BUSINESS_ERROR_DUPLICATE_BARCODE = "Barcode already exists";
    public static final String BUSINESS_ERROR_EXPIRED_PRODUCT = "Product has expired";
}

