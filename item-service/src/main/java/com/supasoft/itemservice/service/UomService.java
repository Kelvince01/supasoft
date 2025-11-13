package com.supasoft.itemservice.service;

import java.util.List;

import com.supasoft.itemservice.dto.request.UomRequest;
import com.supasoft.itemservice.dto.response.UomResponse;

/**
 * Service interface for Unit of Measure operations
 */
public interface UomService {
    
    /**
     * Create a new UOM
     */
    UomResponse createUom(UomRequest request);
    
    /**
     * Update an existing UOM
     */
    UomResponse updateUom(Long uomId, UomRequest request);
    
    /**
     * Get UOM by ID
     */
    UomResponse getUomById(Long uomId);
    
    /**
     * Get UOM by code
     */
    UomResponse getUomByCode(String uomCode);
    
    /**
     * Get all active UOMs
     */
    List<UomResponse> getAllActiveUoms();
    
    /**
     * Get UOMs by type
     */
    List<UomResponse> getUomsByType(String uomType);
    
    /**
     * Get base UOMs
     */
    List<UomResponse> getBaseUoms();
    
    /**
     * Delete UOM (soft delete)
     */
    void deleteUom(Long uomId);
}

