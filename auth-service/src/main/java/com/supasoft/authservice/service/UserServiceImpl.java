package com.supasoft.authservice.service;

import com.supasoft.authservice.dto.response.UserResponse;
import com.supasoft.authservice.entity.Role;
import com.supasoft.authservice.entity.User;
import com.supasoft.authservice.repository.UserRepository;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return mapToUserResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        return mapToUserResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> userPage = userRepository.findAll(pageable);
        
        List<UserResponse> users = userPage.getContent().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
        
        return PagedResponse.of(users, page, size, userPage.getTotalElements());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsers(String keyword) {
        // TODO: Implement search functionality
        List<User> users = userRepository.findAll();
        
        return users.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(keyword.toLowerCase())
                        || user.getEmail().toLowerCase().contains(keyword.toLowerCase())
                        || (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(keyword.toLowerCase()))
                        || (user.getLastName() != null && user.getLastName().toLowerCase().contains(keyword.toLowerCase())))
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserResponse userResponse) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Update user fields
        if (userResponse.getFirstName() != null) {
            user.setFirstName(userResponse.getFirstName());
        }
        if (userResponse.getLastName() != null) {
            user.setLastName(userResponse.getLastName());
        }
        if (userResponse.getPhoneNumber() != null) {
            user.setPhoneNumber(userResponse.getPhoneNumber());
        }
        if (userResponse.getBranchId() != null) {
            user.setBranchId(userResponse.getBranchId());
        }
        
        User updatedUser = userRepository.save(user);
        
        log.info("User updated successfully: {}", updatedUser.getUsername());
        
        return mapToUserResponse(updatedUser);
    }
    
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Soft delete
        user.setIsDeleted(true);
        user.setIsActive(false);
        userRepository.save(user);
        
        log.info("User soft deleted: {}", user.getUsername());
    }
    
    @Override
    @Transactional
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        user.setIsActive(true);
        userRepository.save(user);
        
        log.info("User activated: {}", user.getUsername());
    }
    
    @Override
    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        user.setIsActive(false);
        userRepository.save(user);
        
        log.info("User deactivated: {}", user.getUsername());
    }
    
    @Override
    @Transactional
    public void unlockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        user.setIsLocked(false);
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        userRepository.save(user);
        
        log.info("User unlocked: {}", user.getUsername());
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
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

