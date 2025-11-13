package com.supasoft.common.constant;

/**
 * Application-wide constants
 */
public final class AppConstants {
    
    private AppConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    // Pagination
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String MAX_PAGE_SIZE = "100";
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIRECTION = "desc";
    
    // Date formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss";
    
    // JWT
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "authorities";
    
    // API Paths
    public static final String API_V1 = "/api/v1";
    public static final String AUTH_API = API_V1 + "/auth";
    public static final String PUBLIC_API = API_V1 + "/public";
    
    // Cache Names
    public static final String CACHE_ITEMS = "items";
    public static final String CACHE_PRICES = "prices";
    public static final String CACHE_CATEGORIES = "categories";
    public static final String CACHE_USERS = "users";
    
    // Validation
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    
    // Business Rules
    public static final String DEFAULT_CURRENCY = "KES";
    public static final String DEFAULT_COUNTRY_CODE = "KE";
    public static final String DEFAULT_TIMEZONE = "Africa/Nairobi";
    public static final String VAT_RATE_DEFAULT = "16.00";
    
    // File Upload
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/jpg"};
    public static final String[] ALLOWED_DOCUMENT_TYPES = {"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
}

