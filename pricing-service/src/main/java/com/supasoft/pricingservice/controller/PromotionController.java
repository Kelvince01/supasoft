package com.supasoft.pricingservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.pricingservice.dto.request.CreatePromotionRequest;
import com.supasoft.pricingservice.dto.response.PromotionResponse;
import com.supasoft.pricingservice.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for promotion operations
 */
@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Tag(name = "Promotions", description = "Promotion management endpoints")
public class PromotionController {
    
    private final PromotionService promotionService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create promotion", description = "Creates a new promotion")
    public ResponseEntity<ApiResponse<PromotionResponse>> createPromotion(
            @Valid @RequestBody CreatePromotionRequest request
    ) {
        PromotionResponse response = promotionService.createPromotion(request);
        return ResponseEntity.ok(ApiResponse.created("Promotion created successfully", response));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update promotion", description = "Updates an existing promotion")
    public ResponseEntity<ApiResponse<PromotionResponse>> updatePromotion(
            @Parameter(description = "Promotion ID") @PathVariable Long id,
            @Valid @RequestBody CreatePromotionRequest request
    ) {
        PromotionResponse response = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(ApiResponse.success("Promotion updated successfully", response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get promotion by ID", description = "Retrieves a promotion by its ID")
    public ResponseEntity<ApiResponse<PromotionResponse>> getPromotionById(
            @Parameter(description = "Promotion ID") @PathVariable Long id
    ) {
        PromotionResponse response = promotionService.getPromotionById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/code/{code}")
    @Operation(summary = "Get promotion by code", description = "Retrieves a promotion by its code")
    public ResponseEntity<ApiResponse<PromotionResponse>> getPromotionByCode(
            @Parameter(description = "Promotion Code") @PathVariable String code
    ) {
        PromotionResponse response = promotionService.getPromotionByCode(code);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get active promotions", description = "Retrieves all currently active promotions")
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> getActivePromotions() {
        List<PromotionResponse> response = promotionService.getActivePromotions();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/active/item/{itemId}")
    @Operation(summary = "Get active promotions for item", description = "Retrieves active promotions for a specific item")
    public ResponseEntity<ApiResponse<List<PromotionResponse>>> getActivePromotionsForItem(
            @Parameter(description = "Item ID") @PathVariable Long itemId
    ) {
        List<PromotionResponse> response = promotionService.getActivePromotionsForItem(itemId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping
    @Operation(summary = "Get all promotions", description = "Retrieves all promotions with pagination")
    public ResponseEntity<PagedResponse<PromotionResponse>> getAllPromotions(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<PromotionResponse> promotions = promotionService.getAllPromotions(pageable);
        return ResponseEntity.ok(new PagedResponse<>(promotions));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Delete promotion", description = "Soft deletes a promotion")
    public ResponseEntity<ApiResponse<Void>> deletePromotion(
            @Parameter(description = "Promotion ID") @PathVariable Long id
    ) {
        promotionService.deletePromotion(id);
        return ResponseEntity.ok(ApiResponse.success("Promotion deleted successfully", null));
    }
    
    @PostMapping("/validate/{code}")
    @Operation(summary = "Validate promotion", description = "Validates if a promotion code is active and usable")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> validatePromotion(
            @Parameter(description = "Promotion Code") @PathVariable String code
    ) {
        boolean isValid = promotionService.validatePromotion(code);
        return ResponseEntity.ok(ApiResponse.success(Map.of("valid", isValid)));
    }
}

