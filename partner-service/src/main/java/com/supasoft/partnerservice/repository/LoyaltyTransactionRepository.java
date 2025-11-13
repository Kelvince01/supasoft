package com.supasoft.partnerservice.repository;

import com.supasoft.partnerservice.entity.LoyaltyTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for LoyaltyTransaction entity
 */
@Repository
public interface LoyaltyTransactionRepository extends JpaRepository<LoyaltyTransaction, Long> {
    
    Page<LoyaltyTransaction> findByCustomerId(Long customerId, Pageable pageable);
    
    List<LoyaltyTransaction> findByCustomerIdAndTransactionType(Long customerId, String transactionType);
    
    @Query("SELECT SUM(lt.pointsEarned) FROM LoyaltyTransaction lt WHERE lt.customer.id = :customerId")
    Integer sumPointsEarnedByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT SUM(lt.pointsRedeemed) FROM LoyaltyTransaction lt WHERE lt.customer.id = :customerId")
    Integer sumPointsRedeemedByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT SUM(lt.pointsEarned - lt.pointsRedeemed) FROM LoyaltyTransaction lt WHERE lt.customer.id = :customerId AND (lt.expiryDate IS NULL OR lt.expiryDate > :currentDate)")
    Integer calculateAvailablePoints(@Param("customerId") Long customerId, @Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT lt FROM LoyaltyTransaction lt WHERE lt.expiryDate <= :date AND lt.pointsBalance > 0")
    List<LoyaltyTransaction> findExpiringPoints(@Param("date") LocalDate date);
    
    @Query("SELECT lt FROM LoyaltyTransaction lt WHERE lt.customer.id = :customerId AND lt.referenceNumber = :reference")
    List<LoyaltyTransaction> findByCustomerAndReference(@Param("customerId") Long customerId, @Param("reference") String reference);
}

