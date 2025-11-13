package com.supasoft.pricingservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.entity.CustomerPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Customer Pricing entity
 */
@Repository
public interface CustomerPricingRepository extends JpaRepository<CustomerPricing, Long> {
    
    List<CustomerPricing> findByCustomerId(Long customerId);
    
    List<CustomerPricing> findByCustomerIdAndStatus(Long customerId, Status status);
    
    List<CustomerPricing> findByItemId(Long itemId);
    
    Optional<CustomerPricing> findByCustomerIdAndItemId(Long customerId, Long itemId);
    
    Optional<CustomerPricing> findByCustomerIdAndItemIdAndStatus(Long customerId, Long itemId, Status status);
    
    @Query("SELECT cp FROM CustomerPricing cp WHERE cp.customerId = :customerId " +
           "AND cp.itemId = :itemId AND cp.status = :status " +
           "AND (cp.effectiveDate IS NULL OR cp.effectiveDate <= :date) " +
           "AND (cp.expiryDate IS NULL OR cp.expiryDate >= :date)")
    Optional<CustomerPricing> findActiveCustomerPricing(@Param("customerId") Long customerId,
                                                        @Param("itemId") Long itemId,
                                                        @Param("status") Status status,
                                                        @Param("date") LocalDateTime date);
    
    @Query("SELECT cp FROM CustomerPricing cp WHERE cp.customerId = :customerId " +
           "AND cp.status = :status " +
           "AND (cp.effectiveDate IS NULL OR cp.effectiveDate <= :date) " +
           "AND (cp.expiryDate IS NULL OR cp.expiryDate >= :date)")
    List<CustomerPricing> findAllActiveCustomerPricing(@Param("customerId") Long customerId,
                                                        @Param("status") Status status,
                                                        @Param("date") LocalDateTime date);
    
    @Query("SELECT cp FROM CustomerPricing cp WHERE cp.expiryDate < :currentDate")
    List<CustomerPricing> findExpiredCustomerPricing(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT COUNT(cp) FROM CustomerPricing cp WHERE cp.customerId = :customerId AND cp.status = :status")
    long countByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") Status status);
    
    boolean existsByCustomerIdAndItemId(Long customerId, Long itemId);
}

