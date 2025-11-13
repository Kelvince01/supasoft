package com.supasoft.partnerservice.service;

import com.supasoft.common.dto.PagedResponse;
import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.dto.request.CreateCustomerRequest;
import com.supasoft.partnerservice.dto.request.UpdateCustomerRequest;
import com.supasoft.partnerservice.dto.response.CustomerResponse;
import com.supasoft.partnerservice.entity.Customer;
import com.supasoft.partnerservice.entity.CustomerCategory;
import com.supasoft.partnerservice.exception.CustomerNotFoundException;
import com.supasoft.partnerservice.exception.InsufficientCreditException;
import com.supasoft.partnerservice.exception.InsufficientLoyaltyPointsException;
import com.supasoft.partnerservice.mapper.CustomerMapper;
import com.supasoft.partnerservice.repository.CustomerCategoryRepository;
import com.supasoft.partnerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Implementation of CustomerService
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    private final CustomerRepository customerRepository;
    private final CustomerCategoryRepository categoryRepository;
    private final CustomerMapper customerMapper;
    
    @Override
    @CacheEvict(value = "customers", allEntries = true)
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        log.info("Creating new customer: {}", request.getName());
        
        Customer customer = customerMapper.toEntity(request);
        customer.setCode(generateCustomerCode());
        customer.setRegistrationDate(LocalDate.now());
        
        if (request.getCategoryId() != null) {
            CustomerCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Customer category not found"));
            customer.setCategory(category);
            
            // Apply category defaults if not provided
            if (request.getCreditLimit() == null && category.getDefaultCreditLimit() != null) {
                customer.setCreditLimit(category.getDefaultCreditLimit());
            }
            if (request.getDiscountPercentage() == null) {
                customer.setDiscountPercentage(category.getDefaultDiscountPercentage());
            }
            if (request.getLoyaltyTier() == null) {
                customer.setLoyaltyTier(category.getLoyaltyTier());
            }
        }
        
        customer.updateAvailableCredit();
        
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created successfully: {}", savedCustomer.getCode());
        
        return customerMapper.toResponse(savedCustomer);
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        log.info("Updating customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        customerMapper.updateEntity(request, customer);
        
        if (request.getCategoryId() != null && !request.getCategoryId().equals(customer.getCategory().getId())) {
            CustomerCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Customer category not found"));
            customer.setCategory(category);
        }
        
        if (request.getCreditLimit() != null) {
            customer.updateAvailableCredit();
        }
        
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("Customer updated successfully: {}", updatedCustomer.getCode());
        
        return customerMapper.toResponse(updatedCustomer);
    }
    
    @Override
    @Cacheable(value = "customers", key = "#id")
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        log.debug("Fetching customer by ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        return customerMapper.toResponse(customer);
    }
    
    @Override
    @Cacheable(value = "customers", key = "#code")
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByCode(String code) {
        log.debug("Fetching customer by code: {}", code);
        
        Customer customer = customerRepository.findByCode(code)
                .orElseThrow(() -> new CustomerNotFoundException("code", code));
        
        return customerMapper.toResponse(customer);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CustomerResponse> getAllCustomers(Pageable pageable) {
        log.debug("Fetching all customers, page: {}", pageable.getPageNumber());
        
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        Page<CustomerResponse> responsePage = customerPage.map(customerMapper::toResponse);
        
        return new PagedResponse<>(responsePage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PagedResponse<CustomerResponse> searchCustomers(String search, Pageable pageable) {
        log.debug("Searching customers with query: {}", search);
        
        Page<Customer> customerPage = customerRepository.searchCustomers(search, pageable);
        Page<CustomerResponse> responsePage = customerPage.map(customerMapper::toResponse);
        
        return new PagedResponse<>(responsePage);
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public void deleteCustomer(Long id) {
        log.info("Deleting customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        customer.setStatus(Status.INACTIVE);
        customerRepository.save(customer);
        
        log.info("Customer deleted (soft delete): {}", customer.getCode());
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public void adjustCreditLimit(Long id, BigDecimal newLimit) {
        log.info("Adjusting credit limit for customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        customer.setCreditLimit(newLimit);
        customer.updateAvailableCredit();
        
        customerRepository.save(customer);
        log.info("Credit limit adjusted to {} for customer: {}", newLimit, customer.getCode());
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public void recordSale(Long id, BigDecimal amount) {
        log.info("Recording sale for customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        // Check credit limit if credit is allowed
        if (customer.getIsCreditAllowed()) {
            BigDecimal newBalance = customer.getCurrentBalance().add(amount);
            if (newBalance.compareTo(customer.getCreditLimit()) > 0) {
                throw new InsufficientCreditException(
                        id, 
                        amount, 
                        customer.getAvailableCredit()
                );
            }
            customer.setCurrentBalance(newBalance);
        }
        
        customer.setTotalSales(customer.getTotalSales().add(amount));
        customer.setLastPurchaseDate(LocalDate.now());
        customer.updateAvailableCredit();
        
        customerRepository.save(customer);
        log.info("Sale recorded: {} for customer: {}", amount, customer.getCode());
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public void recordPayment(Long id, BigDecimal amount) {
        log.info("Recording payment for customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        customer.setCurrentBalance(customer.getCurrentBalance().subtract(amount));
        customer.setTotalPayments(customer.getTotalPayments().add(amount));
        customer.updateAvailableCredit();
        
        customerRepository.save(customer);
        log.info("Payment recorded: {} for customer: {}", amount, customer.getCode());
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public void addLoyaltyPoints(Long id, Integer points) {
        log.info("Adding loyalty points for customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        customer.setTotalPointsEarned(customer.getTotalPointsEarned() + points);
        
        customerRepository.save(customer);
        log.info("Loyalty points added: {} for customer: {}", points, customer.getCode());
    }
    
    @Override
    @CacheEvict(value = "customers", key = "#id")
    public void redeemLoyaltyPoints(Long id, Integer points) {
        log.info("Redeeming loyalty points for customer ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        
        if (customer.getLoyaltyPoints() < points) {
            throw new InsufficientLoyaltyPointsException(
                    id,
                    points,
                    customer.getLoyaltyPoints()
            );
        }
        
        customer.setLoyaltyPoints(customer.getLoyaltyPoints() - points);
        customer.setTotalPointsRedeemed(customer.getTotalPointsRedeemed() + points);
        
        customerRepository.save(customer);
        log.info("Loyalty points redeemed: {} for customer: {}", points, customer.getCode());
    }
    
    private String generateCustomerCode() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}

