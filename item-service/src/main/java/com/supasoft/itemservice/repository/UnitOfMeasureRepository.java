package com.supasoft.itemservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.itemservice.entity.UnitOfMeasure;

/**
 * Repository for UnitOfMeasure entity
 */
@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {
    
    /**
     * Find UOM by code
     */
    Optional<UnitOfMeasure> findByUomCode(String uomCode);
    
    /**
     * Find UOM by name
     */
    Optional<UnitOfMeasure> findByUomName(String uomName);
    
    /**
     * Check if UOM code exists
     */
    boolean existsByUomCode(String uomCode);
    
    /**
     * Find all active UOMs
     */
    @Query("SELECT u FROM UnitOfMeasure u WHERE u.isActive = true AND u.isDeleted = false ORDER BY u.uomName")
    List<UnitOfMeasure> findAllActive();
    
    /**
     * Find UOMs by type
     */
    @Query("SELECT u FROM UnitOfMeasure u WHERE u.uomType = :uomType AND u.isActive = true AND u.isDeleted = false ORDER BY u.uomName")
    List<UnitOfMeasure> findByType(@Param("uomType") String uomType);
    
    /**
     * Find base UOMs
     */
    @Query("SELECT u FROM UnitOfMeasure u WHERE u.isBaseUnit = true AND u.isActive = true AND u.isDeleted = false ORDER BY u.uomName")
    List<UnitOfMeasure> findBaseUnits();
    
    /**
     * Find non-base UOMs
     */
    @Query("SELECT u FROM UnitOfMeasure u WHERE u.isBaseUnit = false AND u.isActive = true AND u.isDeleted = false ORDER BY u.uomName")
    List<UnitOfMeasure> findNonBaseUnits();
}

