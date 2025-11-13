package com.supasoft.pricingservice.repository;

import com.supasoft.pricingservice.entity.PriceHistory;
import com.supasoft.pricingservice.entity.PriceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Price History entity
 */
@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {
    
    List<PriceHistory> findByItemId(Long itemId);
    
    Page<PriceHistory> findByItemId(Long itemId, Pageable pageable);
    
    List<PriceHistory> findByItemIdAndPriceType(Long itemId, PriceType priceType);
    
    Page<PriceHistory> findByItemIdAndPriceType(Long itemId, PriceType priceType, Pageable pageable);
    
    List<PriceHistory> findByChangedBy(String changedBy);
    
    @Query("SELECT ph FROM PriceHistory ph WHERE ph.itemId = :itemId " +
           "AND ph.changedAt BETWEEN :startDate AND :endDate " +
           "ORDER BY ph.changedAt DESC")
    List<PriceHistory> findPriceHistoryByDateRange(@Param("itemId") Long itemId,
                                                    @Param("startDate") LocalDateTime startDate,
                                                    @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ph FROM PriceHistory ph WHERE ph.changedAt BETWEEN :startDate AND :endDate " +
           "ORDER BY ph.changedAt DESC")
    List<PriceHistory> findAllPriceHistoryByDateRange(@Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ph FROM PriceHistory ph WHERE ph.itemId = :itemId " +
           "ORDER BY ph.changedAt DESC LIMIT 1")
    PriceHistory findLatestPriceChange(@Param("itemId") Long itemId);
    
    @Query("SELECT COUNT(ph) FROM PriceHistory ph WHERE ph.itemId = :itemId " +
           "AND ph.changedAt >= :date")
    long countPriceChangesSince(@Param("itemId") Long itemId, @Param("date") LocalDateTime date);
}

