package com.supasoft.partnerservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.entity.CustomerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for CustomerCategory entity
 */
@Repository
public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long> {
    
    Optional<CustomerCategory> findByCode(String code);
    
    List<CustomerCategory> findByStatus(Status status);
    
    @Query("SELECT cc FROM CustomerCategory cc WHERE cc.status = :status ORDER BY cc.name ASC")
    List<CustomerCategory> findActiveCategories(@Param("status") Status status);
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.category.id = :categoryId")
    Long countCustomersByCategory(@Param("categoryId") Long categoryId);
}

