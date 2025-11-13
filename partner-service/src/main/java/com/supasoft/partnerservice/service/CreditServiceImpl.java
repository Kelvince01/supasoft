package com.supasoft.partnerservice.service;

import com.supasoft.partnerservice.entity.Customer;
import com.supasoft.partnerservice.exception.CustomerNotFoundException;
import com.supasoft.partnerservice.exception.InsufficientCreditException;
import com.supasoft.partnerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Implementation of CreditService
 * Basic implementation - extend as needed
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreditServiceImpl implements CreditService {
    
    private final CustomerRepository customerRepository;
    
    @Override
    @Transactional(readOnly = true)
    public boolean validateCreditLimit(Long customerId, BigDecimal amount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        return customer.getAvailableCredit().compareTo(amount) >= 0;
    }
    
    @Override
    public void allocateCredit(Long customerId, BigDecimal amount) {
        log.info("Allocating credit for customer {}: {}", customerId, amount);
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        if (!validateCreditLimit(customerId, amount)) {
            throw new InsufficientCreditException(
                    customerId,
                    amount,
                    customer.getAvailableCredit()
            );
        }
        
        customer.setCurrentBalance(customer.getCurrentBalance().add(amount));
        customer.updateAvailableCredit();
        customerRepository.save(customer);
    }
    
    @Override
    public void releaseCredit(Long customerId, BigDecimal amount) {
        log.info("Releasing credit for customer {}: {}", customerId, amount);
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        customer.setCurrentBalance(customer.getCurrentBalance().subtract(amount));
        customer.updateAvailableCredit();
        customerRepository.save(customer);
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getAvailableCredit(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        return customer.getAvailableCredit();
    }
    
    @Override
    public void updateCreditLimit(Long customerId, BigDecimal newLimit) {
        log.info("Updating credit limit for customer {}: {}", customerId, newLimit);
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        customer.setCreditLimit(newLimit);
        customer.updateAvailableCredit();
        customerRepository.save(customer);
    }
}

