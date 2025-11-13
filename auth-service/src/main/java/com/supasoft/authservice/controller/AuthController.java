package com.supasoft.authservice.controller;

import com.supasoft.authservice.dto.request.ChangePasswordRequest;
import com.supasoft.authservice.dto.request.LoginRequest;
import com.supasoft.authservice.dto.request.RefreshTokenRequest;
import com.supasoft.authservice.dto.request.RegisterRequest;
import com.supasoft.authservice.dto.response.LoginResponse;
import com.supasoft.authservice.dto.response.TokenResponse;
import com.supasoft.authservice.dto.response.UserResponse;
import com.supasoft.authservice.service.AuthService;
import com.supasoft.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Register a new user
     */
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        log.info("Registration request received for username: {}", request.getUsername());
        
        UserResponse response = authService.register(request);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response));
    }
    
    /**
     * Login user
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return access token")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        log.info("Login request received for user: {}", request.getUsernameOrEmail());
        
        LoginResponse response = authService.login(request);
        
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
    
    /**
     * Refresh access token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Refresh access token using refresh token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        log.info("Token refresh request received");
        
        TokenResponse response = authService.refreshToken(request);
        
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", response));
    }
    
    /**
     * Logout user
     */
    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user and revoke tokens")
    public ResponseEntity<ApiResponse<Void>> logout(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Logout request received for user: {}", userDetails.getUsername());
        
        authService.logout(userDetails.getUsername());
        
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }
    
    /**
     * Change password
     */
    @PostMapping("/change-password")
    @Operation(summary = "Change password", description = "Change user password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {
        log.info("Password change request received for user: {}", userDetails.getUsername());
        
        authService.changePassword(userDetails.getUsername(), request);
        
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
    
    /**
     * Get current user details
     */
    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Get details of currently authenticated user")
    public ResponseEntity<ApiResponse<UserDetails>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Current user request received for: {}", userDetails.getUsername());
        
        return ResponseEntity.ok(ApiResponse.success("User details retrieved successfully", userDetails));
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if auth service is running")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Auth service is running"));
    }
}

