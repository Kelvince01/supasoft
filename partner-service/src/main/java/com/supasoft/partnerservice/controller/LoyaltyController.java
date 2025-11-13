package com.supasoft.partnerservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.partnerservice.entity.LoyaltyTransaction;
import com.supasoft.partnerservice.service.LoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Loyalty management
 * TODO: Expand with more loyalty operations
 */
@RestController
@RequestMapping("/api/v1/loyalty")
@RequiredArgsConstructor
@Tag(name = "Loyalty", description = "Loyalty points management APIs")
public class LoyaltyController {
    
    private final LoyaltyService loyaltyService;
    
    @GetMapping("/customers/{customerId}/points")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get available loyalty points for customer")
    public ResponseEntity<ApiResponse<Integer>> getAvailablePoints(@PathVariable Long customerId) {
        Integer points = loyaltyService.getAvailablePoints(customerId);
        return ResponseEntity.ok(ApiResponse.success("Points retrieved successfully", points));
    }
    
    @GetMapping("/customers/{customerId}/history")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get loyalty transaction history")
    public ResponseEntity<ApiResponse<Page<LoyaltyTransaction>>> getTransactionHistory(
            @PathVariable Long customerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<LoyaltyTransaction> history = loyaltyService.getTransactionHistory(customerId, pageable);
        return ResponseEntity.ok(ApiResponse.success("History retrieved successfully", history));
    }
    
    @PostMapping("/expire")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Expire old loyalty points")
    public ResponseEntity<ApiResponse<Void>> expirePoints() {
        loyaltyService.expirePoints();
        return ResponseEntity.ok(ApiResponse.success("Points expiration completed", null));
    }
}

