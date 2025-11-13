package com.supasoft.itemservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.itemservice.entity.ItemBarcode;

/**
 * Repository for ItemBarcode entity
 */
@Repository
public interface ItemBarcodeRepository extends JpaRepository<ItemBarcode, Long> {
    
    /**
     * Find barcode by barcode string
     */
    Optional<ItemBarcode> findByBarcode(String barcode);
    
    /**
     * Check if barcode exists
     */
    boolean existsByBarcode(String barcode);
    
    /**
     * Find all barcodes for an item
     */
    @Query("SELECT ib FROM ItemBarcode ib WHERE ib.item.itemId = :itemId AND ib.isActive = true AND ib.isDeleted = false")
    List<ItemBarcode> findByItemId(@Param("itemId") Long itemId);
    
    /**
     * Find primary barcode for an item
     */
    @Query("SELECT ib FROM ItemBarcode ib WHERE ib.item.itemId = :itemId AND ib.isPrimary = true AND ib.isActive = true AND ib.isDeleted = false")
    Optional<ItemBarcode> findPrimaryByItemId(@Param("itemId") Long itemId);
    
    /**
     * Find barcodes by type
     */
    @Query("SELECT ib FROM ItemBarcode ib WHERE ib.barcodeType = :barcodeType AND ib.isActive = true AND ib.isDeleted = false")
    List<ItemBarcode> findByBarcodeType(@Param("barcodeType") String barcodeType);
    
    /**
     * Count barcodes for an item
     */
    @Query("SELECT COUNT(ib) FROM ItemBarcode ib WHERE ib.item.itemId = :itemId AND ib.isActive = true AND ib.isDeleted = false")
    long countByItemId(@Param("itemId") Long itemId);
}

