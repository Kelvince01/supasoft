package com.supasoft.pricingservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.entity.PriceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Price Type entity
 */
@Repository
public interface PriceTypeRepository extends JpaRepository<PriceType, Long> {
    
    Optional<PriceType> findByCode(String code);
    
    Optional<PriceType> findByCodeAndStatus(String code, Status status);
    
    List<PriceType> findByStatus(Status status);
    
    List<PriceType> findByStatusOrderByPriorityAsc(Status status);
    
    Optional<PriceType> findByIsDefaultTrue();
    
    @Query("SELECT pt FROM PriceType pt WHERE pt.status = :status AND pt.isDeleted = false ORDER BY pt.priority ASC")
    List<PriceType> findAllActivePriceTypes(Status status);
    
    boolean existsByCode(String code);
    
    boolean existsByCodeAndIdNot(String code, Long id);
}

