package com.supasoft.itemservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.itemservice.entity.ItemUomConversion;

/**
 * Repository for ItemUomConversion entity
 */
@Repository
public interface ItemUomConversionRepository extends JpaRepository<ItemUomConversion, Long> {
    
    /**
     * Find all conversions for an item
     */
    @Query("SELECT c FROM ItemUomConversion c WHERE c.item.itemId = :itemId AND c.isActive = true AND c.isDeleted = false")
    List<ItemUomConversion> findByItemId(@Param("itemId") Long itemId);
    
    /**
     * Find conversion by item and UOMs
     */
    @Query("SELECT c FROM ItemUomConversion c WHERE c.item.itemId = :itemId " +
           "AND c.fromUom.uomId = :fromUomId AND c.toUom.uomId = :toUomId " +
           "AND c.isActive = true AND c.isDeleted = false")
    Optional<ItemUomConversion> findConversion(
            @Param("itemId") Long itemId,
            @Param("fromUomId") Long fromUomId,
            @Param("toUomId") Long toUomId);
    
    /**
     * Find conversions from specific UOM
     */
    @Query("SELECT c FROM ItemUomConversion c WHERE c.item.itemId = :itemId " +
           "AND c.fromUom.uomId = :fromUomId AND c.isActive = true AND c.isDeleted = false")
    List<ItemUomConversion> findConversionsFromUom(@Param("itemId") Long itemId, @Param("fromUomId") Long fromUomId);
    
    /**
     * Find conversions to specific UOM
     */
    @Query("SELECT c FROM ItemUomConversion c WHERE c.item.itemId = :itemId " +
           "AND c.toUom.uomId = :toUomId AND c.isActive = true AND c.isDeleted = false")
    List<ItemUomConversion> findConversionsToUom(@Param("itemId") Long itemId, @Param("toUomId") Long toUomId);
    
    /**
     * Find conversion by barcode
     */
    Optional<ItemUomConversion> findByBarcode(String barcode);
    
    /**
     * Check if conversion exists
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ItemUomConversion c " +
           "WHERE c.item.itemId = :itemId AND c.fromUom.uomId = :fromUomId " +
           "AND c.toUom.uomId = :toUomId")
    boolean existsConversion(
            @Param("itemId") Long itemId,
            @Param("fromUomId") Long fromUomId,
            @Param("toUomId") Long toUomId);
}

