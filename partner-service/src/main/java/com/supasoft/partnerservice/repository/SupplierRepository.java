package com.supasoft.partnerservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Supplier entity
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    
    Optional<Supplier> findByCode(String code);
    
    Optional<Supplier> findByEmail(String email);
    
    Optional<Supplier> findByPhone(String phone);
    
    Page<Supplier> findByStatus(Status status, Pageable pageable);
    
    @Query("SELECT s FROM Supplier s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.phone) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Supplier> searchSuppliers(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT s FROM Supplier s WHERE s.rating >= :minRating AND s.status = :status ORDER BY s.rating DESC")
    List<Supplier> findByMinimumRating(@Param("minRating") BigDecimal minRating, @Param("status") Status status);
    
    @Query("SELECT s FROM Supplier s WHERE s.isPreferred = true AND s.status = :status")
    List<Supplier> findPreferredSuppliers(@Param("status") Status status);
    
    @Query("SELECT s FROM Supplier s WHERE s.status = :status ORDER BY s.leadTimeDays ASC")
    Page<Supplier> findByShortestLeadTime(@Param("status") Status status, Pageable pageable);
    
    @Query("SELECT s FROM Supplier s WHERE s.status = :status ORDER BY s.totalPurchases DESC")
    Page<Supplier> findTopSuppliers(@Param("status") Status status, Pageable pageable);
    
    @Query("SELECT COUNT(s) FROM Supplier s WHERE s.status = :status")
    Long countByStatus(@Param("status") Status status);
    
    @Query("SELECT SUM(s.totalPurchases) FROM Supplier s WHERE s.status = :status")
    BigDecimal sumTotalPurchasesByStatus(@Param("status") Status status);
}

