package com.supasoft.itemservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supasoft.itemservice.dto.request.CreateItemRequest;
import com.supasoft.itemservice.dto.request.UpdateItemRequest;
import com.supasoft.itemservice.dto.response.ItemDetailResponse;
import com.supasoft.itemservice.dto.response.ItemListResponse;
import com.supasoft.itemservice.dto.response.ItemResponse;

/**
 * Service interface for Item operations
 */
public interface ItemService {
    
    /**
     * Create a new item
     */
    ItemDetailResponse createItem(CreateItemRequest request);
    
    /**
     * Update an existing item
     */
    ItemDetailResponse updateItem(Long itemId, UpdateItemRequest request);
    
    /**
     * Get item by ID (with full details)
     */
    ItemDetailResponse getItemById(Long itemId);
    
    /**
     * Get item by code
     */
    ItemDetailResponse getItemByCode(String itemCode);
    
    /**
     * Get item by barcode
     */
    ItemDetailResponse getItemByBarcode(String barcode);
    
    /**
     * Get all active items with pagination
     */
    Page<ItemResponse> getAllActiveItems(Pageable pageable);
    
    /**
     * Get items for list view (optimized)
     */
    Page<ItemListResponse> getItemsList(Pageable pageable);
    
    /**
     * Search items with filters
     */
    Page<ItemResponse> searchItems(
            String search,
            Long categoryId,
            Long brandId,
            Long supplierId,
            Boolean isActive,
            Boolean isForSale,
            Boolean isForPurchase,
            Pageable pageable
    );
    
    /**
     * Get items by category
     */
    Page<ItemResponse> getItemsByCategory(Long categoryId, Pageable pageable);
    
    /**
     * Get items by brand
     */
    Page<ItemResponse> getItemsByBrand(Long brandId, Pageable pageable);
    
    /**
     * Get items by supplier
     */
    Page<ItemResponse> getItemsBySupplier(Long supplierId, Pageable pageable);
    
    /**
     * Get items for sale
     */
    Page<ItemResponse> getItemsForSale(Pageable pageable);
    
    /**
     * Get items for purchase
     */
    Page<ItemResponse> getItemsForPurchase(Pageable pageable);
    
    /**
     * Delete item (soft delete)
     */
    void deleteItem(Long itemId);
    
    /**
     * Activate item
     */
    void activateItem(Long itemId);
    
    /**
     * Deactivate item
     */
    void deactivateItem(Long itemId);
    
    /**
     * Count active items
     */
    long countActiveItems();
}

