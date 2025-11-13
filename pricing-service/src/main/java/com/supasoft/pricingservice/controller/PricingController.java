package com.supasoft.pricingservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.pricingservice.dto.request.CreatePriceRequest;
import com.supasoft.pricingservice.dto.request.PriceCalculationRequest;
import com.supasoft.pricingservice.dto.request.UpdatePriceRequest;
import com.supasoft.pricingservice.dto.response.PriceCalculationResponse;
import com.supasoft.pricingservice.dto.response.PriceResponse;
import com.supasoft.pricingservice.dto.response.ProfitMarginResponse;
import com.supasoft.pricingservice.service.PriceCalculationService;
import com.supasoft.pricingservice.service.PricingService;
import com.supasoft.pricingservice.service.ProfitMarginService;
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

/**
 * REST controller for pricing operations
 */
@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
@Tag(name = "Pricing", description = "Item pricing management endpoints")
public class PricingController {
    
    private final PricingService pricingService;
    private final PriceCalculationService priceCalculationService;
    private final ProfitMarginService profitMarginService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create item price", description = "Creates a new price for an item")
    public ResponseEntity<ApiResponse<PriceResponse>> createPrice(
            @Valid @RequestBody CreatePriceRequest request
    ) {
        PriceResponse response = pricingService.createPrice(request);
        return ResponseEntity.ok(ApiResponse.created("Price created successfully", response));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update item price", description = "Updates an existing price")
    public ResponseEntity<ApiResponse<PriceResponse>> updatePrice(
            @Parameter(description = "Price ID") @PathVariable Long id,
            @Valid @RequestBody UpdatePriceRequest request
    ) {
        PriceResponse response = pricingService.updatePrice(id, request);
        return ResponseEntity.ok(ApiResponse.success("Price updated successfully", response));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get price by ID", description = "Retrieves a price by its ID")
    public ResponseEntity<ApiResponse<PriceResponse>> getPriceById(
            @Parameter(description = "Price ID") @PathVariable Long id
    ) {
        PriceResponse response = pricingService.getPriceById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/item/{itemId}")
    @Operation(summary = "Get prices by item ID", description = "Retrieves all prices for a specific item")
    public ResponseEntity<ApiResponse<List<PriceResponse>>> getPricesByItemId(
            @Parameter(description = "Item ID") @PathVariable Long itemId
    ) {
        List<PriceResponse> response = pricingService.getPricesByItemId(itemId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/item/{itemId}/type/{priceTypeId}")
    @Operation(summary = "Get active price", description = "Retrieves active price for item and price type")
    public ResponseEntity<ApiResponse<PriceResponse>> getActivePriceByItemAndType(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @Parameter(description = "Price Type ID") @PathVariable Long priceTypeId
    ) {
        PriceResponse response = pricingService.getActivePriceByItemAndType(itemId, priceTypeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping
    @Operation(summary = "Get all prices", description = "Retrieves all prices with pagination")
    public ResponseEntity<PagedResponse<PriceResponse>> getAllPrices(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<PriceResponse> prices = pricingService.getAllPrices(pageable);
        return ResponseEntity.ok(new PagedResponse<>(prices));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Delete price", description = "Soft deletes a price")
    public ResponseEntity<ApiResponse<Void>> deletePrice(
            @Parameter(description = "Price ID") @PathVariable Long id
    ) {
        pricingService.deletePrice(id);
        return ResponseEntity.ok(ApiResponse.success("Price deleted successfully", null));
    }
    
    @GetMapping("/low-margin")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get low margin prices", description = "Retrieves prices below margin threshold")
    public ResponseEntity<ApiResponse<List<PriceResponse>>> getLowMarginPrices(
            @Parameter(description = "Margin threshold percentage") @RequestParam Double threshold
    ) {
        List<PriceResponse> response = pricingService.getLowMarginPrices(threshold);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @PostMapping("/calculate")
    @Operation(summary = "Calculate price", description = "Calculates total price for items")
    public ResponseEntity<ApiResponse<PriceCalculationResponse>> calculatePrice(
            @Valid @RequestBody PriceCalculationRequest request
    ) {
        PriceCalculationResponse response = priceCalculationService.calculatePriceWithAll(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/margin/{itemId}/{priceTypeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Calculate margin", description = "Calculates profit margin for an item")
    public ResponseEntity<ApiResponse<ProfitMarginResponse>> calculateMargin(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @Parameter(description = "Price Type ID") @PathVariable Long priceTypeId
    ) {
        ProfitMarginResponse response = profitMarginService.calculateMargin(itemId, priceTypeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}

