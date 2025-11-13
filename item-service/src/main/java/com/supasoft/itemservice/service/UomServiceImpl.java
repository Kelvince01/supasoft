package com.supasoft.itemservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supasoft.common.exception.BusinessException;
import com.supasoft.common.exception.ResourceNotFoundException;
import com.supasoft.itemservice.dto.request.UomRequest;
import com.supasoft.itemservice.dto.response.UomResponse;
import com.supasoft.itemservice.entity.UnitOfMeasure;
import com.supasoft.itemservice.mapper.UomMapper;
import com.supasoft.itemservice.repository.UnitOfMeasureRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Unit of Measure operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UomServiceImpl implements UomService {
    
    private final UnitOfMeasureRepository uomRepository;
    private final UomMapper uomMapper;
    
    @Override
    public UomResponse createUom(UomRequest request) {
        log.info("Creating UOM with code: {}", request.getUomCode());
        
        // Check if UOM code already exists
        if (uomRepository.existsByUomCode(request.getUomCode())) {
            throw new BusinessException(String.format("UOM with code '%s' already exists", request.getUomCode()));
        }
        
        UnitOfMeasure uom = uomMapper.toEntity(request);
        UnitOfMeasure savedUom = uomRepository.save(uom);
        
        log.info("UOM created successfully with ID: {}", savedUom.getUomId());
        
        return uomMapper.toResponse(savedUom);
    }
    
    @Override
    public UomResponse updateUom(Long uomId, UomRequest request) {
        log.info("Updating UOM with ID: {}", uomId);
        
        UnitOfMeasure uom = uomRepository.findById(uomId)
                .orElseThrow(() -> new ResourceNotFoundException("UnitOfMeasure", "id", uomId));
        
        // Check if new code conflicts with existing UOMs
        if (!uom.getUomCode().equals(request.getUomCode()) &&
            uomRepository.existsByUomCode(request.getUomCode())) {
            throw new BusinessException(String.format("UOM with code '%s' already exists", request.getUomCode()));
        }
        
        uomMapper.updateEntity(request, uom);
        UnitOfMeasure updatedUom = uomRepository.save(uom);
        
        log.info("UOM updated successfully: {}", updatedUom.getUomId());
        
        return uomMapper.toResponse(updatedUom);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UomResponse getUomById(Long uomId) {
        UnitOfMeasure uom = uomRepository.findById(uomId)
                .orElseThrow(() -> new ResourceNotFoundException("UnitOfMeasure", "id", uomId));
        return uomMapper.toResponse(uom);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UomResponse getUomByCode(String uomCode) {
        UnitOfMeasure uom = uomRepository.findByUomCode(uomCode)
                .orElseThrow(() -> new ResourceNotFoundException("UnitOfMeasure", "code", uomCode));
        return uomMapper.toResponse(uom);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UomResponse> getAllActiveUoms() {
        return uomRepository.findAllActive().stream()
                .map(uomMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UomResponse> getUomsByType(String uomType) {
        return uomRepository.findByType(uomType).stream()
                .map(uomMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UomResponse> getBaseUoms() {
        return uomRepository.findBaseUnits().stream()
                .map(uomMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteUom(Long uomId) {
        log.info("Deleting UOM with ID: {}", uomId);
        
        UnitOfMeasure uom = uomRepository.findById(uomId)
                .orElseThrow(() -> new ResourceNotFoundException("UnitOfMeasure", "id", uomId));
        
        // Note: In real implementation, check if UOM is used by any items
        // For now, just soft delete
        uom.setIsActive(false);
        uomRepository.save(uom);
        log.info("UOM deleted successfully: {}", uomId);
    }
}

