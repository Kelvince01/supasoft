package com.supasoft.partnerservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Customer entity
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    
    Optional<Customer> findByCode(String code);
    
    Optional<Customer> findByEmail(String email);
    
    Optional<Customer> findByPhone(String phone);
    
    List<Customer> findByCategoryId(Long categoryId);
    
    Page<Customer> findByStatus(Status status, Pageable pageable);
    
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.phone) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Customer> searchCustomers(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT c FROM Customer c WHERE c.currentBalance > c.creditLimit AND c.status = :status")
    List<Customer> findOverdueCreditCustomers(@Param("status") Status status);
    
    @Query("SELECT c FROM Customer c WHERE c.availableCredit < :threshold AND c.isCreditAllowed = true")
    List<Customer> findLowCreditCustomers(@Param("threshold") BigDecimal threshold);
    
    @Query("SELECT c FROM Customer c WHERE c.status = :status ORDER BY c.totalSales DESC")
    Page<Customer> findTopCustomers(@Param("status") Status status, Pageable pageable);
    
    @Query("SELECT c FROM Customer c WHERE c.lastPurchaseDate < :date AND c.status = :status")
    List<Customer> findInactiveCustomers(@Param("date") LocalDate date, @Param("status") Status status);
    
    @Query("SELECT c FROM Customer c WHERE c.isVip = true AND c.status = :status")
    List<Customer> findVipCustomers(@Param("status") Status status);
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.status = :status")
    Long countByStatus(@Param("status") Status status);
    
    @Query("SELECT SUM(c.totalSales) FROM Customer c WHERE c.status = :status")
    BigDecimal sumTotalSalesByStatus(@Param("status") Status status);
}

