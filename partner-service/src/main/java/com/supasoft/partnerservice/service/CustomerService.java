package com.supasoft.partnerservice.service;

import com.supasoft.common.dto.PagedResponse;
import com.supasoft.partnerservice.dto.request.CreateCustomerRequest;
import com.supasoft.partnerservice.dto.request.UpdateCustomerRequest;
import com.supasoft.partnerservice.dto.response.CustomerResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service interface for Customer management
 */
public interface CustomerService {
    
    CustomerResponse createCustomer(CreateCustomerRequest request);
    
    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);
    
    CustomerResponse getCustomerById(Long id);
    
    CustomerResponse getCustomerByCode(String code);
    
    PagedResponse<CustomerResponse> getAllCustomers(Pageable pageable);
    
    PagedResponse<CustomerResponse> searchCustomers(String search, Pageable pageable);
    
    void deleteCustomer(Long id);
    
    void adjustCreditLimit(Long id, BigDecimal newLimit);
    
    void recordSale(Long id, BigDecimal amount);
    
    void recordPayment(Long id, BigDecimal amount);
    
    void addLoyaltyPoints(Long id, Integer points);
    
    void redeemLoyaltyPoints(Long id, Integer points);
}

