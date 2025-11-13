package com.supasoft.pricingservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.entity.ItemPrice;
import com.supasoft.pricingservice.entity.PriceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Item Price entity
 */
@Repository
public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
    
    List<ItemPrice> findByItemId(Long itemId);
    
    List<ItemPrice> findByItemIdAndStatus(Long itemId, Status status);
    
    Optional<ItemPrice> findByItemIdAndPriceType(Long itemId, PriceType priceType);
    
    Optional<ItemPrice> findByItemIdAndPriceTypeAndStatus(Long itemId, PriceType priceType, Status status);
    
    List<ItemPrice> findByPriceType(PriceType priceType);
    
    @Query("SELECT ip FROM ItemPrice ip WHERE ip.itemId = :itemId AND ip.status = :status " +
           "AND (ip.effectiveDate IS NULL OR ip.effectiveDate <= :date) " +
           "AND (ip.expiryDate IS NULL OR ip.expiryDate >= :date)")
    List<ItemPrice> findActiveItemPrices(@Param("itemId") Long itemId, 
                                         @Param("status") Status status, 
                                         @Param("date") LocalDateTime date);
    
    @Query("SELECT ip FROM ItemPrice ip WHERE ip.itemId = :itemId " +
           "AND ip.priceType.id = :priceTypeId AND ip.status = :status " +
           "AND (ip.effectiveDate IS NULL OR ip.effectiveDate <= :date) " +
           "AND (ip.expiryDate IS NULL OR ip.expiryDate >= :date)")
    Optional<ItemPrice> findActiveItemPriceByType(@Param("itemId") Long itemId,
                                                   @Param("priceTypeId") Long priceTypeId,
                                                   @Param("status") Status status,
                                                   @Param("date") LocalDateTime date);
    
    @Query("SELECT ip FROM ItemPrice ip WHERE ip.profitMargin < :threshold AND ip.status = :status")
    List<ItemPrice> findLowMarginPrices(@Param("threshold") Double threshold, @Param("status") Status status);
    
    @Query("SELECT COUNT(ip) FROM ItemPrice ip WHERE ip.itemId = :itemId AND ip.status = :status")
    long countActiveItemPrices(@Param("itemId") Long itemId, @Param("status") Status status);
    
    boolean existsByItemIdAndPriceType(Long itemId, PriceType priceType);
}

