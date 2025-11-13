package com.supasoft.authservice.controller;

import com.supasoft.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for role management operations
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "Role management endpoints")
public class RoleController {
    
    /**
     * Get all roles
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get all roles", description = "Get all available roles")
    public ResponseEntity<ApiResponse<String>> getAllRoles() {
        log.info("Get all roles request received");
        
        // TODO: Implement role retrieval logic
        
        return ResponseEntity.ok(ApiResponse.success("Roles retrieved successfully"));
    }
}

