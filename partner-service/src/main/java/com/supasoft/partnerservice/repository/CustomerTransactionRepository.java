package com.supasoft.partnerservice.repository;

import com.supasoft.partnerservice.entity.CustomerTransaction;
import com.supasoft.partnerservice.enums.PartnerTransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for CustomerTransaction entity
 */
@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {
    
    Page<CustomerTransaction> findByCustomerId(Long customerId, Pageable pageable);
    
    List<CustomerTransaction> findByCustomerIdAndTransactionType(Long customerId, PartnerTransactionType transactionType);
    
    @Query("SELECT ct FROM CustomerTransaction ct WHERE ct.customer.id = :customerId ORDER BY ct.transactionDate DESC")
    Page<CustomerTransaction> findByCustomerOrderByDateDesc(@Param("customerId") Long customerId, Pageable pageable);
    
    @Query("SELECT SUM(ct.amount) FROM CustomerTransaction ct WHERE ct.customer.id = :customerId AND ct.transactionType = :type")
    BigDecimal sumAmountByCustomerAndType(@Param("customerId") Long customerId, @Param("type") PartnerTransactionType type);
    
    @Query("SELECT ct FROM CustomerTransaction ct WHERE ct.customer.id = :customerId AND ct.transactionDate BETWEEN :startDate AND :endDate")
    List<CustomerTransaction> findByCustomerAndDateRange(
            @Param("customerId") Long customerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT ct FROM CustomerTransaction ct WHERE ct.referenceNumber = :reference")
    List<CustomerTransaction> findByReferenceNumber(@Param("reference") String reference);
    
    @Query("SELECT ct.balanceAfter FROM CustomerTransaction ct WHERE ct.customer.id = :customerId ORDER BY ct.transactionDate DESC")
    BigDecimal findLatestBalance(@Param("customerId") Long customerId, Pageable pageable);
}

