package com.supasoft.itemservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.itemservice.entity.Brand;

/**
 * Repository for Brand entity
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
    /**
     * Find brand by code
     */
    Optional<Brand> findByBrandCode(String brandCode);
    
    /**
     * Find brand by name
     */
    Optional<Brand> findByBrandName(String brandName);
    
    /**
     * Check if brand code exists
     */
    boolean existsByBrandCode(String brandCode);
    
    /**
     * Check if brand name exists
     */
    boolean existsByBrandName(String brandName);
    
    /**
     * Find all active brands
     */
    @Query("SELECT b FROM Brand b WHERE b.isActive = true AND b.isDeleted = false ORDER BY b.brandName")
    List<Brand> findAllActive();
    
    /**
     * Find brands with pagination
     */
    @Query("SELECT b FROM Brand b WHERE b.isActive = true AND b.isDeleted = false")
    Page<Brand> findAllActive(Pageable pageable);
    
    /**
     * Search brands by name
     */
    @Query("SELECT b FROM Brand b WHERE LOWER(b.brandName) LIKE LOWER(CONCAT('%', :search, '%')) AND b.isActive = true AND b.isDeleted = false")
    List<Brand> searchByName(@Param("search") String search);
    
    /**
     * Find brands by manufacturer
     */
    @Query("SELECT b FROM Brand b WHERE LOWER(b.manufacturer) LIKE LOWER(CONCAT('%', :manufacturer, '%')) AND b.isActive = true AND b.isDeleted = false")
    List<Brand> findByManufacturer(@Param("manufacturer") String manufacturer);
    
    /**
     * Find brands by country
     */
    List<Brand> findByCountryOfOriginAndIsActiveTrue(String countryOfOrigin);
    
    /**
     * Count items by brand
     */
    @Query("SELECT COUNT(i) FROM Item i WHERE i.brand.brandId = :brandId AND i.isActive = true AND i.isDeleted = false")
    long countItemsByBrand(@Param("brandId") Long brandId);
}

