package com.supasoft.authservice.service;

import com.supasoft.authservice.dto.response.UserResponse;
import com.supasoft.common.dto.PagedResponse;

import java.util.List;

/**
 * Service interface for user management operations
 */
public interface UserService {
    
    /**
     * Get user by ID
     */
    UserResponse getUserById(Long userId);
    
    /**
     * Get user by username
     */
    UserResponse getUserByUsername(String username);
    
    /**
     * Get all users with pagination
     */
    PagedResponse<UserResponse> getAllUsers(int page, int size);
    
    /**
     * Search users by keyword
     */
    List<UserResponse> searchUsers(String keyword);
    
    /**
     * Update user details
     */
    UserResponse updateUser(Long userId, UserResponse userResponse);
    
    /**
     * Delete user (soft delete)
     */
    void deleteUser(Long userId);
    
    /**
     * Activate user
     */
    void activateUser(Long userId);
    
    /**
     * Deactivate user
     */
    void deactivateUser(Long userId);
    
    /**
     * Unlock user account
     */
    void unlockUser(Long userId);
}

