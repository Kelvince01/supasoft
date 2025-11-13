package com.supasoft.itemservice.controller;

import java.util.List;

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

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.itemservice.dto.request.UomRequest;
import com.supasoft.itemservice.dto.response.UomResponse;
import com.supasoft.itemservice.service.UomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for Unit of Measure operations
 */
@RestController
@RequestMapping("/api/v1/uoms")
@RequiredArgsConstructor
@Tag(name = "Unit of Measure Management", description = "APIs for managing units of measure")
public class UnitOfMeasureController {
    
    private final UomService uomService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('CREATE_ITEMS')")
    @Operation(summary = "Create a new UOM")
    public ResponseEntity<ApiResponse<UomResponse>> createUom(@Valid @RequestBody UomRequest request) {
        UomResponse response = uomService.createUom(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("UOM created successfully", response));
    }
    
    @PutMapping("/{uomId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Update a UOM")
    public ResponseEntity<ApiResponse<UomResponse>> updateUom(
            @PathVariable Long uomId,
            @Valid @RequestBody UomRequest request) {
        UomResponse response = uomService.updateUom(uomId, request);
        return ResponseEntity.ok(ApiResponse.success("UOM updated successfully", response));
    }
    
    @GetMapping("/{uomId}")
    @Operation(summary = "Get UOM by ID")
    public ResponseEntity<ApiResponse<UomResponse>> getUomById(@PathVariable Long uomId) {
        UomResponse response = uomService.getUomById(uomId);
        return ResponseEntity.ok(ApiResponse.success("UOM retrieved successfully", response));
    }
    
    @GetMapping("/code/{uomCode}")
    @Operation(summary = "Get UOM by code")
    public ResponseEntity<ApiResponse<UomResponse>> getUomByCode(@PathVariable String uomCode) {
        UomResponse response = uomService.getUomByCode(uomCode);
        return ResponseEntity.ok(ApiResponse.success("UOM retrieved successfully", response));
    }
    
    @GetMapping
    @Operation(summary = "Get all active UOMs")
    public ResponseEntity<ApiResponse<List<UomResponse>>> getAllUoms() {
        List<UomResponse> uoms = uomService.getAllActiveUoms();
        return ResponseEntity.ok(ApiResponse.success("UOMs retrieved successfully", uoms));
    }
    
    @GetMapping("/type/{uomType}")
    @Operation(summary = "Get UOMs by type")
    public ResponseEntity<ApiResponse<List<UomResponse>>> getUomsByType(@PathVariable String uomType) {
        List<UomResponse> uoms = uomService.getUomsByType(uomType);
        return ResponseEntity.ok(ApiResponse.success("UOMs retrieved successfully", uoms));
    }
    
    @GetMapping("/base")
    @Operation(summary = "Get base UOMs")
    public ResponseEntity<ApiResponse<List<UomResponse>>> getBaseUoms() {
        List<UomResponse> uoms = uomService.getBaseUoms();
        return ResponseEntity.ok(ApiResponse.success("Base UOMs retrieved successfully", uoms));
    }
    
    @DeleteMapping("/{uomId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('DELETE_ITEMS')")
    @Operation(summary = "Delete a UOM")
    public ResponseEntity<ApiResponse<Void>> deleteUom(@PathVariable Long uomId) {
        uomService.deleteUom(uomId);
        return ResponseEntity.ok(ApiResponse.success("UOM deleted successfully", null));
    }
}

