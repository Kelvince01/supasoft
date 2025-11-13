package com.supasoft.authservice.service;

import com.supasoft.authservice.dto.request.ChangePasswordRequest;
import com.supasoft.authservice.dto.request.LoginRequest;
import com.supasoft.authservice.dto.request.RefreshTokenRequest;
import com.supasoft.authservice.dto.request.RegisterRequest;
import com.supasoft.authservice.dto.response.LoginResponse;
import com.supasoft.authservice.dto.response.TokenResponse;
import com.supasoft.authservice.dto.response.UserResponse;

/**
 * Service interface for authentication operations
 */
public interface AuthService {
    
    /**
     * Register a new user
     */
    UserResponse register(RegisterRequest request);
    
    /**
     * Authenticate user and return tokens
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * Refresh access token using refresh token
     */
    TokenResponse refreshToken(RefreshTokenRequest request);
    
    /**
     * Logout user (revoke refresh tokens)
     */
    void logout(String username);
    
    /**
     * Change user password
     */
    void changePassword(String username, ChangePasswordRequest request);
    
    /**
     * Verify email
     */
    void verifyEmail(String token);
    
    /**
     * Request password reset
     */
    void requestPasswordReset(String email);
    
    /**
     * Reset password
     */
    void resetPassword(String token, String newPassword);
}

