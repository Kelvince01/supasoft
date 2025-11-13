package com.supasoft.pricingservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.pricingservice.dto.request.CreateDiscountRequest;
import com.supasoft.pricingservice.dto.response.DiscountResponse;
import com.supasoft.pricingservice.service.DiscountService;
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
 * REST controller for discount operations
 */
@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Tag(name = "Discounts", description = "Discount management endpoints")
public class DiscountController {
    
    private final DiscountService discountService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create discount", description = "Creates a new discount")
    public ResponseEntity<ApiResponse<DiscountResponse>> createDiscount(
            @Valid @RequestBody CreateDiscountRequest request
    ) {
        DiscountResponse response = discountService.createDiscount(request);
        return ResponseEntity.ok(ApiResponse.created("Discount created successfully", response));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update discount", description = "Updates an existing discount")
    public ResponseEntity<ApiResponse<DiscountResponse>> updateDiscount(
            @Parameter(description = "Discount ID") @PathVariable Long id,
            @Valid @RequestBody CreateDiscountRequest request
    ) {
        DiscountResponse response = discountService.updateDiscount(id, request);
        return ResponseEntity.ok(ApiResponse.success("Discount updated successfully", response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get discount by ID", description = "Retrieves a discount by its ID")
    public ResponseEntity<ApiResponse<DiscountResponse>> getDiscountById(
            @Parameter(description = "Discount ID") @PathVariable Long id
    ) {
        DiscountResponse response = discountService.getDiscountById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/code/{code}")
    @Operation(summary = "Get discount by code", description = "Retrieves a discount by its code")
    public ResponseEntity<ApiResponse<DiscountResponse>> getDiscountByCode(
            @Parameter(description = "Discount Code") @PathVariable String code
    ) {
        DiscountResponse response = discountService.getDiscountByCode(code);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get active discounts", description = "Retrieves all currently active discounts")
    public ResponseEntity<ApiResponse<List<DiscountResponse>>> getActiveDiscounts() {
        List<DiscountResponse> response = discountService.getActiveDiscounts();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/active/item/{itemId}")
    @Operation(summary = "Get active discounts for item", description = "Retrieves active discounts for a specific item")
    public ResponseEntity<ApiResponse<List<DiscountResponse>>> getActiveDiscountsForItem(
            @Parameter(description = "Item ID") @PathVariable Long itemId
    ) {
        List<DiscountResponse> response = discountService.getActiveDiscountsForItem(itemId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping
    @Operation(summary = "Get all discounts", description = "Retrieves all discounts with pagination")
    public ResponseEntity<PagedResponse<DiscountResponse>> getAllDiscounts(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<DiscountResponse> discounts = discountService.getAllDiscounts(pageable);
        return ResponseEntity.ok(new PagedResponse<>(discounts));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Delete discount", description = "Soft deletes a discount")
    public ResponseEntity<ApiResponse<Void>> deleteDiscount(
            @Parameter(description = "Discount ID") @PathVariable Long id
    ) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok(ApiResponse.success("Discount deleted successfully", null));
    }
    
    @PostMapping("/validate/{code}")
    @Operation(summary = "Validate discount", description = "Validates if a discount code is active and usable")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> validateDiscount(
            @Parameter(description = "Discount Code") @PathVariable String code
    ) {
        boolean isValid = discountService.validateDiscount(code);
        return ResponseEntity.ok(ApiResponse.success(Map.of("valid", isValid)));
    }
}

