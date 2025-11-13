package com.supasoft.itemservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supasoft.itemservice.dto.request.CreateBrandRequest;
import com.supasoft.itemservice.dto.response.BrandResponse;

/**
 * Service interface for Brand operations
 */
public interface BrandService {
    
    /**
     * Create a new brand
     */
    BrandResponse createBrand(CreateBrandRequest request);
    
    /**
     * Update an existing brand
     */
    BrandResponse updateBrand(Long brandId, CreateBrandRequest request);
    
    /**
     * Get brand by ID
     */
    BrandResponse getBrandById(Long brandId);
    
    /**
     * Get brand by code
     */
    BrandResponse getBrandByCode(String brandCode);
    
    /**
     * Get all active brands
     */
    List<BrandResponse> getAllActiveBrands();
    
    /**
     * Get brands with pagination
     */
    Page<BrandResponse> getAllBrands(Pageable pageable);
    
    /**
     * Search brands by name
     */
    List<BrandResponse> searchBrands(String searchTerm);
    
    /**
     * Get brands by manufacturer
     */
    List<BrandResponse> getBrandsByManufacturer(String manufacturer);
    
    /**
     * Delete brand (soft delete)
     */
    void deleteBrand(Long brandId);
}

