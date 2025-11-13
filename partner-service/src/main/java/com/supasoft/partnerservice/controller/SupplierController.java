package com.supasoft.partnerservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.partnerservice.entity.Supplier;
import com.supasoft.partnerservice.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Supplier management
 * TODO: Add full CRUD operations with DTOs
 */
@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@Tag(name = "Supplier", description = "Supplier management APIs")
public class SupplierController {
    
    private final SupplierService supplierService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new supplier")
    public ResponseEntity<ApiResponse<Supplier>> createSupplier(@RequestBody Supplier supplier) {
        Supplier response = supplierService.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Supplier created successfully", response));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get supplier by ID")
    public ResponseEntity<ApiResponse<Supplier>> getSupplierById(@PathVariable Long id) {
        Supplier response = supplierService.getSupplierById(id);
        return ResponseEntity.ok(ApiResponse.success("Supplier retrieved successfully", response));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get all suppliers (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<Supplier>>> getAllSuppliers(
            @PageableDefault(size = 20) Pageable pageable) {
        PagedResponse<Supplier> response = supplierService.getAllSuppliers(pageable);
        return ResponseEntity.ok(ApiResponse.success("Suppliers retrieved successfully", response));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a supplier (soft delete)")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(ApiResponse.success("Supplier deleted successfully", null));
    }
}

