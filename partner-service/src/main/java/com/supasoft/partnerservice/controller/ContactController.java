package com.supasoft.partnerservice.controller;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.partnerservice.entity.Address;
import com.supasoft.partnerservice.entity.ContactPerson;
import com.supasoft.partnerservice.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Contact and Address management
 * TODO: Add full CRUD operations with DTOs
 */
@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Contact and address management APIs")
public class ContactController {
    
    private final ContactService contactService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new contact person")
    public ResponseEntity<ApiResponse<ContactPerson>> createContact(@RequestBody ContactPerson contact) {
        ContactPerson response = contactService.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Contact created successfully", response));
    }
    
    @GetMapping("/customers/{customerId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get contacts for customer")
    public ResponseEntity<ApiResponse<List<ContactPerson>>> getContactsByCustomer(
            @PathVariable Long customerId) {
        List<ContactPerson> contacts = contactService.getContactsByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success("Contacts retrieved successfully", contacts));
    }
    
    @GetMapping("/suppliers/{supplierId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get contacts for supplier")
    public ResponseEntity<ApiResponse<List<ContactPerson>>> getContactsBySupplier(
            @PathVariable Long supplierId) {
        List<ContactPerson> contacts = contactService.getContactsBySupplierId(supplierId);
        return ResponseEntity.ok(ApiResponse.success("Contacts retrieved successfully", contacts));
    }
    
    @PostMapping("/addresses")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new address")
    public ResponseEntity<ApiResponse<Address>> createAddress(@RequestBody Address address) {
        Address response = contactService.createAddress(address);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Address created successfully", response));
    }
    
    @GetMapping("/addresses/customers/{customerId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @Operation(summary = "Get addresses for customer")
    public ResponseEntity<ApiResponse<List<Address>>> getAddressesByCustomer(
            @PathVariable Long customerId) {
        List<Address> addresses = contactService.getAddressesByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success("Addresses retrieved successfully", addresses));
    }
}

