package com.supasoft.itemservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.itemservice.entity.Item;

/**
 * Repository for Item entity
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    
    /**
     * Find item by item code
     */
    Optional<Item> findByItemCode(String itemCode);
    
    /**
     * Find item by barcode
     */
    Optional<Item> findByBarcode(String barcode);
    
    /**
     * Find item by SKU
     */
    Optional<Item> findBySku(String sku);
    
    /**
     * Check if item code exists
     */
    boolean existsByItemCode(String itemCode);
    
    /**
     * Check if barcode exists
     */
    boolean existsByBarcode(String barcode);
    
    /**
     * Find active items by category
     */
    @Query("SELECT i FROM Item i WHERE i.category.categoryId = :categoryId AND i.isActive = true AND i.isDeleted = false")
    Page<Item> findActiveItemsByCategory(@Param("categoryId") Long categoryId, Pageable pageable);
    
    /**
     * Find active items by brand
     */
    @Query("SELECT i FROM Item i WHERE i.brand.brandId = :brandId AND i.isActive = true AND i.isDeleted = false")
    Page<Item> findActiveItemsByBrand(@Param("brandId") Long brandId, Pageable pageable);
    
    /**
     * Find active items by supplier
     */
    @Query("SELECT i FROM Item i WHERE i.supplier.supplierId = :supplierId AND i.isActive = true AND i.isDeleted = false")
    Page<Item> findActiveItemsBySupplier(@Param("supplierId") Long supplierId, Pageable pageable);
    
    /**
     * Search items by keyword (item name, code, barcode)
     */
    @Query("SELECT i FROM Item i WHERE " +
           "(LOWER(i.itemName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(i.itemCode) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(i.barcode) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND i.isActive = true AND i.isDeleted = false")
    Page<Item> searchItems(@Param("search") String search, Pageable pageable);
    
    /**
     * Find all active items
     */
    @Query("SELECT i FROM Item i WHERE i.isActive = true AND i.isDeleted = false")
    Page<Item> findAllActive(Pageable pageable);
    
    /**
     * Find items for sale
     */
    @Query("SELECT i FROM Item i WHERE i.isForSale = true AND i.isActive = true AND i.isDeleted = false")
    Page<Item> findAllForSale(Pageable pageable);
    
    /**
     * Find items for purchase
     */
    @Query("SELECT i FROM Item i WHERE i.isForPurchase = true AND i.isActive = true AND i.isDeleted = false")
    Page<Item> findAllForPurchase(Pageable pageable);
    
    /**
     * Find items with expiry tracking
     */
    @Query("SELECT i FROM Item i WHERE i.hasExpiry = true AND i.isActive = true AND i.isDeleted = false")
    Page<Item> findItemsWithExpiry(Pageable pageable);
    
    /**
     * Find items below reorder level (requires join with stock table)
     */
    @Query("SELECT i FROM Item i WHERE i.trackInventory = true AND i.isActive = true AND i.isDeleted = false ORDER BY i.itemName")
    Page<Item> findItemsForReorder(Pageable pageable);
    
    /**
     * Count active items
     */
    @Query("SELECT COUNT(i) FROM Item i WHERE i.isActive = true AND i.isDeleted = false")
    long countActive();
    
    /**
     * Count items by category
     */
    @Query("SELECT COUNT(i) FROM Item i WHERE i.category.categoryId = :categoryId AND i.isActive = true AND i.isDeleted = false")
    long countByCategory(@Param("categoryId") Long categoryId);
    
    /**
     * Count items by brand
     */
    @Query("SELECT COUNT(i) FROM Item i WHERE i.brand.brandId = :brandId AND i.isActive = true AND i.isDeleted = false")
    long countByBrand(@Param("brandId") Long brandId);
}
