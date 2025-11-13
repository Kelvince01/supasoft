package com.supasoft.partnerservice.service;

import com.supasoft.common.dto.PagedResponse;
import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.entity.Supplier;
import com.supasoft.partnerservice.exception.SupplierNotFoundException;
import com.supasoft.partnerservice.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of SupplierService
 * Basic implementation - extend as needed
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SupplierServiceImpl implements SupplierService {
    
    private final SupplierRepository supplierRepository;
    
    @Override
    public Supplier createSupplier(Supplier supplier) {
        log.info("Creating new supplier: {}", supplier.getName());
        supplier.setCode(generateSupplierCode());
        return supplierRepository.save(supplier);
    }
    
    @Override
    public Supplier updateSupplier(Long id, Supplier supplier) {
        log.info("Updating supplier ID: {}", id);
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));
        // TODO: Implement update logic with mapper
        return supplierRepository.save(existing);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplierByCode(String code) {
        return supplierRepository.findByCode(code)
                .orElseThrow(() -> new SupplierNotFoundException("code", code));
    }
    
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<Supplier> getAllSuppliers(Pageable pageable) {
        Page<Supplier> page = supplierRepository.findAll(pageable);
        return new PagedResponse<>(page);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<Supplier> searchSuppliers(String search, Pageable pageable) {
        Page<Supplier> page = supplierRepository.searchSuppliers(search, pageable);
        return new PagedResponse<>(page);
    }
    
    @Override
    public void deleteSupplier(Long id) {
        log.info("Deleting supplier ID: {}", id);
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));
        supplier.setStatus(Status.INACTIVE);
        supplierRepository.save(supplier);
    }
    
    private String generateSupplierCode() {
        return "SUPP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

