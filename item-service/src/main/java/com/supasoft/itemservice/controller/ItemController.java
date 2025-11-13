package com.supasoft.itemservice.controller;

import com.supasoft.common.constant.AppConstants;
import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.itemservice.dto.request.CreateItemRequest;
import com.supasoft.itemservice.dto.request.UpdateItemRequest;
import com.supasoft.itemservice.dto.response.ItemDetailResponse;
import com.supasoft.itemservice.dto.response.ItemListResponse;
import com.supasoft.itemservice.dto.response.ItemResponse;
import com.supasoft.itemservice.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Item operations
 */
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Item Management", description = "APIs for managing items/products")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('CREATE_ITEMS')")
    @Operation(summary = "Create a new item")
    public ResponseEntity<ApiResponse<ItemDetailResponse>> createItem(
            @Valid @RequestBody CreateItemRequest request) {
        ItemDetailResponse response = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Item created successfully", response));
    }

    @PutMapping("/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Update an item")
    public ResponseEntity<ApiResponse<ItemDetailResponse>> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateItemRequest request) {
        ItemDetailResponse response = itemService.updateItem(itemId, request);
        return ResponseEntity.ok(ApiResponse.success("Item updated successfully", response));
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Get item by ID")
    public ResponseEntity<ApiResponse<ItemDetailResponse>> getItemById(@PathVariable Long itemId) {
        ItemDetailResponse response = itemService.getItemById(itemId);
        return ResponseEntity.ok(ApiResponse.success("Item retrieved successfully", response));
    }

    @GetMapping("/code/{itemCode}")
    @Operation(summary = "Get item by code")
    public ResponseEntity<ApiResponse<ItemDetailResponse>> getItemByCode(@PathVariable String itemCode) {
        ItemDetailResponse response = itemService.getItemByCode(itemCode);
        return ResponseEntity.ok(ApiResponse.success("Item retrieved successfully", response));
    }

    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Get item by barcode")
    public ResponseEntity<ApiResponse<ItemDetailResponse>> getItemByBarcode(@PathVariable String barcode) {
        ItemDetailResponse response = itemService.getItemByBarcode(barcode);
        return ResponseEntity.ok(ApiResponse.success("Item retrieved successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all active items with pagination")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> getAllItems(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "itemName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ItemResponse> items = itemService.getAllActiveItems(pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items retrieved successfully", pagedResponse));
    }

    @GetMapping("/list")
    @Operation(summary = "Get items list (optimized for list views)")
    public ResponseEntity<ApiResponse<PagedResponse<ItemListResponse>>> getItemsList(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "itemName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ItemListResponse> items = itemService.getItemsList(pageable);
        PagedResponse<ItemListResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items list retrieved successfully", pagedResponse));
    }

    @GetMapping("/search")
    @Operation(summary = "Search items with filters")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> searchItems(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Boolean isForSale,
            @RequestParam(required = false) Boolean isForPurchase,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "itemName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ItemResponse> items = itemService.searchItems(
                search, categoryId, brandId, supplierId, isActive, isForSale, isForPurchase, pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items found", pagedResponse));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get items by category")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> getItemsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ItemResponse> items = itemService.getItemsByCategory(categoryId, pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items retrieved successfully", pagedResponse));
    }

    @GetMapping("/brand/{brandId}")
    @Operation(summary = "Get items by brand")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> getItemsByBrand(
            @PathVariable Long brandId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ItemResponse> items = itemService.getItemsByBrand(brandId, pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items retrieved successfully", pagedResponse));
    }

    @GetMapping("/supplier/{supplierId}")
    @Operation(summary = "Get items by supplier")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> getItemsBySupplier(
            @PathVariable Long supplierId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ItemResponse> items = itemService.getItemsBySupplier(supplierId, pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items retrieved successfully", pagedResponse));
    }

    @GetMapping("/for-sale")
    @Operation(summary = "Get items available for sale")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> getItemsForSale(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ItemResponse> items = itemService.getItemsForSale(pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items for sale retrieved successfully", pagedResponse));
    }

    @GetMapping("/for-purchase")
    @Operation(summary = "Get items available for purchase")
    public ResponseEntity<ApiResponse<PagedResponse<ItemResponse>>> getItemsForPurchase(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ItemResponse> items = itemService.getItemsForPurchase(pageable);
        PagedResponse<ItemResponse> pagedResponse = new PagedResponse<>(items);

        return ResponseEntity.ok(ApiResponse.success("Items for purchase retrieved successfully", pagedResponse));
    }

    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('DELETE_ITEMS')")
    @Operation(summary = "Delete an item")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Item deleted successfully", null));
    }

    @PutMapping("/{itemId}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Activate an item")
    public ResponseEntity<ApiResponse<Void>> activateItem(@PathVariable Long itemId) {
        itemService.activateItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Item activated successfully", null));
    }

    @PutMapping("/{itemId}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Deactivate an item")
    public ResponseEntity<ApiResponse<Void>> deactivateItem(@PathVariable Long itemId) {
        itemService.deactivateItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Item deactivated successfully", null));
    }

    @GetMapping("/count")
    @Operation(summary = "Count active items")
    public ResponseEntity<ApiResponse<Long>> countActiveItems() {
        long count = itemService.countActiveItems();
        return ResponseEntity.ok(ApiResponse.success("Active items count", count));
    }
}
