package com.supasoft.authservice.service;

import com.supasoft.authservice.dto.request.ChangePasswordRequest;
import com.supasoft.authservice.dto.request.LoginRequest;
import com.supasoft.authservice.dto.request.RefreshTokenRequest;
import com.supasoft.authservice.dto.request.RegisterRequest;
import com.supasoft.authservice.dto.response.LoginResponse;
import com.supasoft.authservice.dto.response.TokenResponse;
import com.supasoft.authservice.dto.response.UserResponse;
import com.supasoft.authservice.entity.RefreshToken;
import com.supasoft.authservice.entity.Role;
import com.supasoft.authservice.entity.User;
import com.supasoft.authservice.exception.InvalidCredentialsException;
import com.supasoft.authservice.exception.TokenExpiredException;
import com.supasoft.authservice.exception.UserAlreadyExistsException;
import com.supasoft.authservice.repository.RefreshTokenRepository;
import com.supasoft.authservice.repository.RoleRepository;
import com.supasoft.authservice.repository.UserRepository;
import com.supasoft.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of AuthService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final int LOCK_DURATION_MINUTES = 30;
    
    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());
        
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }
        
        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .branchId(request.getBranchId())
                .isActive(true)
                .isEmailVerified(false)
                .isPhoneVerified(false)
                .isLocked(false)
                .failedLoginAttempts(0)
                .build();
        
        // Assign roles
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            roles = roleRepository.findByRoleNameIn(request.getRoles());
        } else {
            // Default role
            roleRepository.findByRoleName("ROLE_CASHIER")
                    .ifPresent(roles::add);
        }
        user.setRoles(roles);
        
        // Save user
        User savedUser = userRepository.save(user);
        
        log.info("User registered successfully: {}", savedUser.getUsername());
        
        return mapToUserResponse(savedUser);
    }
    
    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.info("Login attempt for user: {}", request.getUsernameOrEmail());
        
        // Find user
        User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
        
        // Check if account is locked
        if (!user.isAccountNonLocked()) {
            throw new LockedException("Account is locked. Please try again later.");
        }
        
        try {
            // Authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            request.getPassword()
                    )
            );
            
            // Reset failed attempts on successful login
            if (user.getFailedLoginAttempts() > 0) {
                user.setFailedLoginAttempts(0);
                user.setLockedUntil(null);
                userRepository.save(user);
            }
            
            // Update last login
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
            
            // Generate tokens
            String accessToken = jwtTokenService.generateTokenForUser(user);
            String refreshToken = jwtTokenService.generateRefreshToken(user.getUsername());
            
            // Save refresh token
            saveRefreshToken(user, refreshToken);
            
            log.info("User logged in successfully: {}", user.getUsername());
            
            // Build response
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtTokenService.getJwtExpiration())
                    .user(mapToLoginUserResponse(user))
                    .build();
            
        } catch (BadCredentialsException ex) {
            // Handle failed login attempt
            handleFailedLoginAttempt(user);
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user: {}", request.getUsernameOrEmail(), ex);
            throw new InvalidCredentialsException("Authentication failed: " + ex.getMessage());
        }
    }
    
    @Override
    @Transactional
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        String refreshTokenStr = request.getRefreshToken();
        
        // Validate refresh token
        if (!jwtTokenService.validateToken(refreshTokenStr)) {
            throw new TokenExpiredException("Invalid or expired refresh token");
        }
        
        // Get username from token
        String username = jwtTokenService.getUsernameFromToken(refreshTokenStr);
        
        // Find refresh token in database
        RefreshToken refreshToken = refreshTokenRepository.findValidByToken(refreshTokenStr, LocalDateTime.now())
                .orElseThrow(() -> new TokenExpiredException("Refresh token not found or expired"));
        
        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Generate new access token
        String newAccessToken = jwtTokenService.generateTokenForUser(user);
        
        log.info("Access token refreshed for user: {}", username);
        
        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenStr)
                .tokenType("Bearer")
                .expiresIn(jwtTokenService.getJwtExpiration())
                .build();
    }
    
    @Override
    @Transactional
    public void logout(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Revoke all refresh tokens for the user
        refreshTokenRepository.revokeAllUserTokens(user, LocalDateTime.now());
        
        log.info("User logged out successfully: {}", username);
    }
    
    @Override
    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        // Validate new password matches confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }
        
        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setPasswordChangedAt(LocalDateTime.now());
        userRepository.save(user);
        
        // Revoke all existing tokens
        refreshTokenRepository.revokeAllUserTokens(user, LocalDateTime.now());
        
        log.info("Password changed successfully for user: {}", username);
    }
    
    @Override
    public void verifyEmail(String token) {
        // TODO: Implement email verification logic
        log.info("Email verification requested with token: {}", token);
    }
    
    @Override
    public void requestPasswordReset(String email) {
        // TODO: Implement password reset request logic
        log.info("Password reset requested for email: {}", email);
    }
    
    @Override
    public void resetPassword(String token, String newPassword) {
        // TODO: Implement password reset logic
        log.info("Password reset requested with token: {}", token);
    }
    
    /**
     * Save refresh token to database
     */
    private void saveRefreshToken(User user, String token) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();
        
        refreshTokenRepository.save(refreshToken);
    }
    
    /**
     * Handle failed login attempt
     */
    private void handleFailedLoginAttempt(User user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);
        
        if (attempts >= MAX_LOGIN_ATTEMPTS) {
            user.setIsLocked(true);
            user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES));
            log.warn("Account locked due to multiple failed login attempts: {}", user.getUsername());
        }
        
        userRepository.save(user);
    }
    
    /**
     * Map User entity to UserResponse DTO
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .isActive(user.getIsActive())
                .isEmailVerified(user.getIsEmailVerified())
                .isPhoneVerified(user.getIsPhoneVerified())
                .roles(user.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet()))
                .branchId(user.getBranchId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
    
    /**
     * Map User entity to Login UserResponse DTO
     */
    private LoginResponse.UserResponse mapToLoginUserResponse(User user) {
        return LoginResponse.UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toSet()))
                .branchId(user.getBranchId())
                .build();
    }
}

