package com.supasoft.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for file operations
 */
@Slf4j
public final class FileUtil {
    
    private FileUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    /**
     * Get file extension
     */
    public static String getFileExtension(String filename) {
        if (StringUtil.isEmpty(filename)) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 ? filename.substring(lastDotIndex + 1) : "";
    }
    
    /**
     * Generate unique filename
     */
    public static String generateUniqueFilename(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String uniqueId = UUID.randomUUID().toString();
        return StringUtil.isEmpty(extension) ? uniqueId : uniqueId + "." + extension;
    }
    
    /**
     * Check if file exists
     */
    public static boolean fileExists(String filepath) {
        return filepath != null && Files.exists(Paths.get(filepath));
    }
    
    /**
     * Create directory if not exists
     */
    public static boolean createDirectoryIfNotExists(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            return true;
        } catch (IOException e) {
            log.error("Error creating directory: {}", directoryPath, e);
            return false;
        }
    }
    
    /**
     * Delete file
     */
    public static boolean deleteFile(String filepath) {
        try {
            if (fileExists(filepath)) {
                Files.delete(Paths.get(filepath));
                return true;
            }
            return false;
        } catch (IOException e) {
            log.error("Error deleting file: {}", filepath, e);
            return false;
        }
    }
    
    /**
     * Read file as bytes
     */
    public static byte[] readFileAsBytes(String filepath) {
        try {
            return Files.readAllBytes(Paths.get(filepath));
        } catch (IOException e) {
            log.error("Error reading file: {}", filepath, e);
            return new byte[0];
        }
    }
    
    /**
     * Write bytes to file
     */
    public static boolean writeBytesToFile(byte[] data, String filepath) {
        try {
            Files.write(Paths.get(filepath), data);
            return true;
        } catch (IOException e) {
            log.error("Error writing file: {}", filepath, e);
            return false;
        }
    }
    
    /**
     * Get file size in bytes
     */
    public static long getFileSize(String filepath) {
        try {
            if (fileExists(filepath)) {
                return Files.size(Paths.get(filepath));
            }
            return 0;
        } catch (IOException e) {
            log.error("Error getting file size: {}", filepath, e);
            return 0;
        }
    }
    
    /**
     * Format file size to human readable string
     */
    public static String formatFileSize(long sizeInBytes) {
        if (sizeInBytes < 1024) {
            return sizeInBytes + " B";
        } else if (sizeInBytes < 1024 * 1024) {
            return String.format("%.2f KB", sizeInBytes / 1024.0);
        } else if (sizeInBytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", sizeInBytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", sizeInBytes / (1024.0 * 1024 * 1024));
        }
    }
}

