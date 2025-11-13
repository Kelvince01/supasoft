package com.supasoft.partnerservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.common.dto.PagedResponse;
import com.supasoft.partnerservice.dto.request.CreateCustomerRequest;
import com.supasoft.partnerservice.dto.request.UpdateCustomerRequest;
import com.supasoft.partnerservice.dto.response.CustomerResponse;
import com.supasoft.partnerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * REST Controller for Customer management
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer management APIs")
public class CustomerController {
    
    private final CustomerService customerService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new customer")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Customer created successfully", response));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Update an existing customer")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully", response));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(ApiResponse.success("Customer retrieved successfully", response));
    }
    
    @GetMapping("/code/{code}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get customer by code")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByCode(@PathVariable String code) {
        CustomerResponse response = customerService.getCustomerByCode(code);
        return ResponseEntity.ok(ApiResponse.success("Customer retrieved successfully", response));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get all customers (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<CustomerResponse>>> getAllCustomers(
            @PageableDefault(size = 20) Pageable pageable) {
        PagedResponse<CustomerResponse> response = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(ApiResponse.success("Customers retrieved successfully", response));
    }
    
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Search customers")
    public ResponseEntity<ApiResponse<PagedResponse<CustomerResponse>>> searchCustomers(
            @RequestParam String query,
            @PageableDefault(size = 20) Pageable pageable) {
        PagedResponse<CustomerResponse> response = customerService.searchCustomers(query, pageable);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", response));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a customer (soft delete)")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ApiResponse.success("Customer deleted successfully", null));
    }
    
    @PatchMapping("/{id}/credit-limit")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Adjust customer credit limit")
    public ResponseEntity<ApiResponse<Void>> adjustCreditLimit(
            @PathVariable Long id,
            @RequestParam BigDecimal newLimit) {
        customerService.adjustCreditLimit(id, newLimit);
        return ResponseEntity.ok(ApiResponse.success("Credit limit adjusted successfully", null));
    }
    
    @PostMapping("/{id}/sales")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    @Operation(summary = "Record a sale for customer")
    public ResponseEntity<ApiResponse<Void>> recordSale(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        customerService.recordSale(id, amount);
        return ResponseEntity.ok(ApiResponse.success("Sale recorded successfully", null));
    }
    
    @PostMapping("/{id}/payments")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    @Operation(summary = "Record a payment from customer")
    public ResponseEntity<ApiResponse<Void>> recordPayment(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        customerService.recordPayment(id, amount);
        return ResponseEntity.ok(ApiResponse.success("Payment recorded successfully", null));
    }
    
    @PostMapping("/{id}/loyalty/earn")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    @Operation(summary = "Add loyalty points to customer")
    public ResponseEntity<ApiResponse<Void>> addLoyaltyPoints(
            @PathVariable Long id,
            @RequestParam Integer points) {
        customerService.addLoyaltyPoints(id, points);
        return ResponseEntity.ok(ApiResponse.success("Loyalty points added successfully", null));
    }
    
    @PostMapping("/{id}/loyalty/redeem")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'USER')")
    @Operation(summary = "Redeem loyalty points for customer")
    public ResponseEntity<ApiResponse<Void>> redeemLoyaltyPoints(
            @PathVariable Long id,
            @RequestParam Integer points) {
        customerService.redeemLoyaltyPoints(id, points);
        return ResponseEntity.ok(ApiResponse.success("Loyalty points redeemed successfully", null));
    }
}

