package com.supasoft.authservice.controller;

import com.supasoft.authservice.dto.response.UserResponse;
import com.supasoft.authservice.service.UserService;
import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for user management operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User management endpoints")
public class UserController {
    
    private final UserService userService;
    
    /**
     * Get all users with pagination
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @Operation(summary = "Get all users", description = "Get all users with pagination")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Get all users request received - page: {}, size: {}", page, size);
        
        PagedResponse<UserResponse> response = userService.getAllUsers(page, size);
        
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", response));
    }
    
    /**
     * Get user by ID
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @Operation(summary = "Get user by ID", description = "Get user details by ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long userId) {
        log.info("Get user by ID request received: {}", userId);
        
        UserResponse response = userService.getUserById(userId);
        
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }
    
    /**
     * Get user by username
     */
    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @Operation(summary = "Get user by username", description = "Get user details by username")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByUsername(
            @PathVariable String username) {
        log.info("Get user by username request received: {}", username);
        
        UserResponse response = userService.getUserByUsername(username);
        
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }
    
    /**
     * Search users
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @Operation(summary = "Search users", description = "Search users by keyword")
    public ResponseEntity<ApiResponse<List<UserResponse>>> searchUsers(
            @RequestParam String keyword) {
        log.info("Search users request received with keyword: {}", keyword);
        
        List<UserResponse> response = userService.searchUsers(keyword);
        
        return ResponseEntity.ok(ApiResponse.success("Users found successfully", response));
    }
    
    /**
     * Update user
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @Operation(summary = "Update user", description = "Update user details")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long userId,
            @RequestBody UserResponse userResponse) {
        log.info("Update user request received for ID: {}", userId);
        
        UserResponse response = userService.updateUser(userId, userResponse);
        
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", response));
    }
    
    /**
     * Delete user
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete user", description = "Soft delete user")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        log.info("Delete user request received for ID: {}", userId);
        
        userService.deleteUser(userId);
        
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
    
    /**
     * Activate user
     */
    @PutMapping("/{userId}/activate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Activate user", description = "Activate user account")
    public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable Long userId) {
        log.info("Activate user request received for ID: {}", userId);
        
        userService.activateUser(userId);
        
        return ResponseEntity.ok(ApiResponse.success("User activated successfully", null));
    }
    
    /**
     * Deactivate user
     */
    @PutMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Deactivate user", description = "Deactivate user account")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long userId) {
        log.info("Deactivate user request received for ID: {}", userId);
        
        userService.deactivateUser(userId);
        
        return ResponseEntity.ok(ApiResponse.success("User deactivated successfully", null));
    }
    
    /**
     * Unlock user
     */
    @PutMapping("/{userId}/unlock")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Unlock user", description = "Unlock user account")
    public ResponseEntity<ApiResponse<Void>> unlockUser(@PathVariable Long userId) {
        log.info("Unlock user request received for ID: {}", userId);
        
        userService.unlockUser(userId);
        
        return ResponseEntity.ok(ApiResponse.success("User unlocked successfully", null));
    }
}

