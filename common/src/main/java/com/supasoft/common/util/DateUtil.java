package com.supasoft.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for date and time operations
 */
@Slf4j
public final class DateUtil {
    
    private DateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Africa/Nairobi");
    
    /**
     * Get current date time
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(DEFAULT_ZONE_ID);
    }
    
    /**
     * Get current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now(DEFAULT_ZONE_ID);
    }
    
    /**
     * Format date to string
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)) : null;
    }
    
    /**
     * Format date time to string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT)) : null;
    }
    
    /**
     * Format date time with custom pattern
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern(pattern)) : null;
    }
    
    /**
     * Parse string to LocalDate
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return dateString != null ? LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)) : null;
        } catch (Exception e) {
            log.error("Error parsing date: {}", dateString, e);
            return null;
        }
    }
    
    /**
     * Parse string to LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return dateTimeString != null ? LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT)) : null;
        } catch (Exception e) {
            log.error("Error parsing date time: {}", dateTimeString, e);
            return null;
        }
    }
    
    /**
     * Check if date is in the past
     */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(getCurrentDate());
    }
    
    /**
     * Check if date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(getCurrentDate());
    }
    
    /**
     * Calculate days between two dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null ? ChronoUnit.DAYS.between(startDate, endDate) : 0;
    }
    
    /**
     * Add days to date
     */
    public static LocalDate addDays(LocalDate date, long days) {
        return date != null ? date.plusDays(days) : null;
    }
    
    /**
     * Subtract days from date
     */
    public static LocalDate subtractDays(LocalDate date, long days) {
        return date != null ? date.minusDays(days) : null;
    }
    
    /**
     * Get start of day
     */
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }
    
    /**
     * Get end of day
     */
    public static LocalDateTime getEndOfDay(LocalDate date) {
        return date != null ? date.atTime(23, 59, 59) : null;
    }
    
    /**
     * Convert LocalDateTime to timestamp
     */
    public static long toTimestamp(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli() : 0;
    }
    
    /**
     * Convert timestamp to LocalDateTime
     */
    public static LocalDateTime fromTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), DEFAULT_ZONE_ID);
    }
}

