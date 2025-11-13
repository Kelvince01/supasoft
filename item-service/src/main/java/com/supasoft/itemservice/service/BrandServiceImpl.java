package com.supasoft.itemservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supasoft.common.exception.BusinessException;
import com.supasoft.common.exception.ResourceNotFoundException;
import com.supasoft.itemservice.dto.request.CreateBrandRequest;
import com.supasoft.itemservice.dto.response.BrandResponse;
import com.supasoft.itemservice.entity.Brand;
import com.supasoft.itemservice.mapper.BrandMapper;
import com.supasoft.itemservice.repository.BrandRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Brand operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BrandServiceImpl implements BrandService {
    
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    
    @Override
    public BrandResponse createBrand(CreateBrandRequest request) {
        log.info("Creating brand with code: {}", request.getBrandCode());
        
        // Check if brand code already exists
        if (brandRepository.existsByBrandCode(request.getBrandCode())) {
            throw new BusinessException(String.format("Brand with code '%s' already exists", request.getBrandCode()));
        }
        
        // Check if brand name already exists
        if (brandRepository.existsByBrandName(request.getBrandName())) {
            throw new BusinessException(String.format("Brand with name '%s' already exists", request.getBrandName()));
        }
        
        Brand brand = brandMapper.toEntity(request);
        Brand savedBrand = brandRepository.save(brand);
        
        log.info("Brand created successfully with ID: {}", savedBrand.getBrandId());
        
        BrandResponse response = brandMapper.toResponse(savedBrand);
        response.setItemCount(0L);
        return response;
    }
    
    @Override
    public BrandResponse updateBrand(Long brandId, CreateBrandRequest request) {
        log.info("Updating brand with ID: {}", brandId);
        
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
        
        // Check if new code conflicts with existing brands
        if (!brand.getBrandCode().equals(request.getBrandCode()) &&
            brandRepository.existsByBrandCode(request.getBrandCode())) {
            throw new BusinessException(String.format("Brand with code '%s' already exists", request.getBrandCode()));
        }
        
        // Check if new name conflicts with existing brands
        if (!brand.getBrandName().equals(request.getBrandName()) &&
            brandRepository.existsByBrandName(request.getBrandName())) {
            throw new BusinessException(String.format("Brand with name '%s' already exists", request.getBrandName()));
        }
        
        brandMapper.updateEntity(request, brand);
        Brand updatedBrand = brandRepository.save(brand);
        
        log.info("Brand updated successfully: {}", updatedBrand.getBrandId());
        
        return enrichBrandResponse(brandMapper.toResponse(updatedBrand));
    }
    
    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
        return enrichBrandResponse(brandMapper.toResponse(brand));
    }
    
    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandByCode(String brandCode) {
        Brand brand = brandRepository.findByBrandCode(brandCode)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "code", brandCode));
        return enrichBrandResponse(brandMapper.toResponse(brand));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getAllActiveBrands() {
        return brandRepository.findAllActive().stream()
                .map(brandMapper::toResponse)
                .map(this::enrichBrandResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<BrandResponse> getAllBrands(Pageable pageable) {
        return brandRepository.findAllActive(pageable)
                .map(brandMapper::toResponse)
                .map(this::enrichBrandResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> searchBrands(String searchTerm) {
        return brandRepository.searchByName(searchTerm).stream()
                .map(brandMapper::toResponse)
                .map(this::enrichBrandResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getBrandsByManufacturer(String manufacturer) {
        return brandRepository.findByManufacturer(manufacturer).stream()
                .map(brandMapper::toResponse)
                .map(this::enrichBrandResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteBrand(Long brandId) {
        log.info("Deleting brand with ID: {}", brandId);
        
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", brandId));
        
        // Check if brand has items
        long itemCount = brandRepository.countItemsByBrand(brandId);
        if (itemCount > 0) {
            throw new BusinessException(String.format("Cannot delete brand with %d items. Please reassign items first.", itemCount));
        }
        
        brand.setIsActive(false);
        brandRepository.save(brand);
        log.info("Brand deleted successfully: {}", brandId);
    }
    
    /**
     * Enrich brand response with item count
     */
    private BrandResponse enrichBrandResponse(BrandResponse response) {
        response.setItemCount(brandRepository.countItemsByBrand(response.getBrandId()));
        return response;
    }
}

