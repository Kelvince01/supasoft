package com.supasoft.pricingservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.pricingservice.entity.PriceHistory;
import com.supasoft.pricingservice.repository.PriceHistoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for price history operations
 */
@RestController
@RequestMapping("/api/v1/price-history")
@RequiredArgsConstructor
@Tag(name = "Price History", description = "Price history and audit trail endpoints")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class PriceHistoryController {
    
    private final PriceHistoryRepository priceHistoryRepository;
    
    @GetMapping("/item/{itemId}")
    @Operation(summary = "Get price history by item", description = "Retrieves price change history for an item")
    public ResponseEntity<PagedResponse<PriceHistory>> getPriceHistoryByItem(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<PriceHistory> history = priceHistoryRepository.findByItemId(itemId, pageable);
        return ResponseEntity.ok(new PagedResponse<>(history));
    }
    
    @GetMapping("/item/{itemId}/range")
    @Operation(summary = "Get price history by date range", description = "Retrieves price changes within a date range")
    public ResponseEntity<ApiResponse<List<PriceHistory>>> getPriceHistoryByDateRange(
            @Parameter(description = "Item ID") @PathVariable Long itemId,
            @Parameter(description = "Start date") @RequestParam LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam LocalDateTime endDate
    ) {
        List<PriceHistory> history = priceHistoryRepository.findPriceHistoryByDateRange(
                itemId, startDate, endDate
        );
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/user/{username}")
    @Operation(summary = "Get price changes by user", description = "Retrieves all price changes made by a specific user")
    public ResponseEntity<ApiResponse<List<PriceHistory>>> getPriceHistoryByUser(
            @Parameter(description = "Username") @PathVariable String username
    ) {
        List<PriceHistory> history = priceHistoryRepository.findByChangedBy(username);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/item/{itemId}/latest")
    @Operation(summary = "Get latest price change", description = "Retrieves the most recent price change for an item")
    public ResponseEntity<ApiResponse<PriceHistory>> getLatestPriceChange(
            @Parameter(description = "Item ID") @PathVariable Long itemId
    ) {
        PriceHistory history = priceHistoryRepository.findLatestPriceChange(itemId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/recent")
    @Operation(summary = "Get recent price changes", description = "Retrieves all price changes within a time period")
    public ResponseEntity<ApiResponse<List<PriceHistory>>> getRecentPriceChanges(
            @Parameter(description = "Start date") @RequestParam LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam LocalDateTime endDate
    ) {
        List<PriceHistory> history = priceHistoryRepository.findAllPriceHistoryByDateRange(
                startDate, endDate
        );
        return ResponseEntity.ok(ApiResponse.success(history));
    }
}

