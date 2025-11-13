package com.supasoft.pricingservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.entity.Promotion;
import com.supasoft.pricingservice.enums.PromotionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Promotion entity
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    Optional<Promotion> findByCode(String code);
    
    Optional<Promotion> findByCodeAndStatus(String code, Status status);
    
    List<Promotion> findByStatus(Status status);
    
    List<Promotion> findByPromotionType(PromotionType promotionType);
    
    @Query("SELECT p FROM Promotion p WHERE p.status = :status " +
           "AND p.startDate <= :currentDate AND p.endDate >= :currentDate " +
           "AND (p.usageLimit IS NULL OR p.usageCount < p.usageLimit) " +
           "ORDER BY p.priority ASC")
    List<Promotion> findActivePromotions(@Param("status") Status status,
                                        @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT DISTINCT p FROM Promotion p " +
           "JOIN p.promotionItems pi " +
           "WHERE p.status = :status " +
           "AND p.startDate <= :currentDate AND p.endDate >= :currentDate " +
           "AND (p.usageLimit IS NULL OR p.usageCount < p.usageLimit) " +
           "AND pi.itemId = :itemId " +
           "ORDER BY p.priority ASC")
    List<Promotion> findActivePromotionsForItem(@Param("itemId") Long itemId,
                                                 @Param("status") Status status,
                                                 @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT p FROM Promotion p WHERE p.status = :status " +
           "AND p.startDate <= :currentDate AND p.endDate >= :currentDate " +
           "AND (p.usageLimit IS NULL OR p.usageCount < p.usageLimit) " +
           "AND p.isCumulative = true " +
           "ORDER BY p.priority ASC")
    List<Promotion> findCumulativePromotions(@Param("status") Status status,
                                             @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT p FROM Promotion p WHERE p.endDate < :currentDate")
    List<Promotion> findExpiredPromotions(@Param("currentDate") LocalDateTime currentDate);
    
    boolean existsByCode(String code);
    
    boolean existsByCodeAndIdNot(String code, Long id);
}

