package com.supasoft.partnerservice.service;

import com.supasoft.common.dto.PagedResponse;
import com.supasoft.partnerservice.entity.Supplier;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for Supplier management
 * TODO: Implement full CRUD operations similar to CustomerService
 */
public interface SupplierService {
    
    Supplier createSupplier(Supplier supplier);
    
    Supplier updateSupplier(Long id, Supplier supplier);
    
    Supplier getSupplierById(Long id);
    
    Supplier getSupplierByCode(String code);
    
    PagedResponse<Supplier> getAllSuppliers(Pageable pageable);
    
    PagedResponse<Supplier> searchSuppliers(String search, Pageable pageable);
    
    void deleteSupplier(Long id);
}

