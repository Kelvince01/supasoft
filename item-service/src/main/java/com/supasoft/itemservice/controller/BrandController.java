package com.supasoft.itemservice.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supasoft.common.constant.AppConstants;
import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.itemservice.dto.request.CreateBrandRequest;
import com.supasoft.itemservice.dto.response.BrandResponse;
import com.supasoft.itemservice.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for Brand operations
 */
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@Tag(name = "Brand Management", description = "APIs for managing product brands")
public class BrandController {
    
    private final BrandService brandService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('CREATE_ITEMS')")
    @Operation(summary = "Create a new brand")
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(
            @Valid @RequestBody CreateBrandRequest request) {
        BrandResponse response = brandService.createBrand(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Brand created successfully", response));
    }
    
    @PutMapping("/{brandId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Update a brand")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable Long brandId,
            @Valid @RequestBody CreateBrandRequest request) {
        BrandResponse response = brandService.updateBrand(brandId, request);
        return ResponseEntity.ok(ApiResponse.success("Brand updated successfully", response));
    }
    
    @GetMapping("/{brandId}")
    @Operation(summary = "Get brand by ID")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable Long brandId) {
        BrandResponse response = brandService.getBrandById(brandId);
        return ResponseEntity.ok(ApiResponse.success("Brand retrieved successfully", response));
    }
    
    @GetMapping("/code/{brandCode}")
    @Operation(summary = "Get brand by code")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandByCode(@PathVariable String brandCode) {
        BrandResponse response = brandService.getBrandByCode(brandCode);
        return ResponseEntity.ok(ApiResponse.success("Brand retrieved successfully", response));
    }
    
    @GetMapping("/list")
    @Operation(summary = "Get all active brands (list)")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrandsList() {
        List<BrandResponse> brands = brandService.getAllActiveBrands();
        return ResponseEntity.ok(ApiResponse.success("Brands retrieved successfully", brands));
    }
    
    @GetMapping
    @Operation(summary = "Get all brands with pagination")
    public ResponseEntity<ApiResponse<PagedResponse<BrandResponse>>> getAllBrands(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "brandName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        
        Page<BrandResponse> brands = brandService.getAllBrands(pageable);
        PagedResponse<BrandResponse> pagedResponse = new PagedResponse<>(brands);
        
        return ResponseEntity.ok(ApiResponse.success("Brands retrieved successfully", pagedResponse));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search brands by name")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> searchBrands(@RequestParam String searchTerm) {
        List<BrandResponse> brands = brandService.searchBrands(searchTerm);
        return ResponseEntity.ok(ApiResponse.success("Brands found", brands));
    }
    
    @GetMapping("/manufacturer")
    @Operation(summary = "Get brands by manufacturer")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getBrandsByManufacturer(
            @RequestParam String manufacturer) {
        List<BrandResponse> brands = brandService.getBrandsByManufacturer(manufacturer);
        return ResponseEntity.ok(ApiResponse.success("Brands retrieved successfully", brands));
    }
    
    @DeleteMapping("/{brandId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('DELETE_ITEMS')")
    @Operation(summary = "Delete a brand")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok(ApiResponse.success("Brand deleted successfully", null));
    }
}

