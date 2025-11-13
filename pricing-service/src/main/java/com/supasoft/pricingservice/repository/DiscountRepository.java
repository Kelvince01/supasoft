package com.supasoft.pricingservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.entity.Discount;
import com.supasoft.pricingservice.enums.DiscountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Discount entity
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    
    Optional<Discount> findByCode(String code);
    
    Optional<Discount> findByCodeAndStatus(String code, Status status);
    
    List<Discount> findByStatus(Status status);
    
    List<Discount> findByDiscountType(DiscountType discountType);
    
    @Query("SELECT d FROM Discount d WHERE d.status = :status " +
           "AND d.startDate <= :currentDate AND d.endDate >= :currentDate " +
           "AND (d.usageLimit IS NULL OR d.usageCount < d.usageLimit)")
    List<Discount> findActiveDiscounts(@Param("status") Status status, 
                                       @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT d FROM Discount d WHERE d.status = :status " +
           "AND d.startDate <= :currentDate AND d.endDate >= :currentDate " +
           "AND (d.usageLimit IS NULL OR d.usageCount < d.usageLimit) " +
           "AND d.appliesToItems = true " +
           "AND d.itemIds LIKE CONCAT('%', :itemId, '%')")
    List<Discount> findActiveDiscountsForItem(@Param("itemId") Long itemId,
                                               @Param("status") Status status,
                                               @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT d FROM Discount d WHERE d.status = :status " +
           "AND d.startDate <= :currentDate AND d.endDate >= :currentDate " +
           "AND (d.usageLimit IS NULL OR d.usageCount < d.usageLimit) " +
           "AND d.isCumulative = true")
    List<Discount> findCumulativeDiscounts(@Param("status") Status status,
                                           @Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT d FROM Discount d WHERE d.endDate < :currentDate")
    List<Discount> findExpiredDiscounts(@Param("currentDate") LocalDateTime currentDate);
    
    boolean existsByCode(String code);
    
    boolean existsByCodeAndIdNot(String code, Long id);
}

