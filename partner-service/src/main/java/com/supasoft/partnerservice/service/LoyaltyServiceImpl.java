package com.supasoft.partnerservice.service;

import com.supasoft.partnerservice.entity.Customer;
import com.supasoft.partnerservice.entity.LoyaltyTransaction;
import com.supasoft.partnerservice.exception.CustomerNotFoundException;
import com.supasoft.partnerservice.repository.CustomerRepository;
import com.supasoft.partnerservice.repository.LoyaltyTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Implementation of LoyaltyService
 * Basic implementation - extend as needed
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LoyaltyServiceImpl implements LoyaltyService {
    
    private final LoyaltyTransactionRepository loyaltyTransactionRepository;
    private final CustomerRepository customerRepository;
    
    @Override
    public Integer calculatePointsForPurchase(Long customerId, BigDecimal purchaseAmount) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        BigDecimal multiplier = customer.getCategory() != null 
                ? customer.getCategory().getPointsPerCurrency()
                : BigDecimal.ONE;
        
        return purchaseAmount.multiply(multiplier).intValue();
    }
    
    @Override
    public void earnPoints(Long customerId, Integer points, String reference) {
        log.info("Customer {} earning {} points", customerId, points);
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        LoyaltyTransaction transaction = new LoyaltyTransaction();
        transaction.setCustomer(customer);
        transaction.setTransactionType("EARN");
        transaction.setPointsEarned(points);
        transaction.setPointsBalance(customer.getLoyaltyPoints() + points);
        transaction.setReferenceNumber(reference);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setExpiryDate(LocalDate.now().plusMonths(12)); // 12 months expiry
        
        loyaltyTransactionRepository.save(transaction);
    }
    
    @Override
    public void redeemPoints(Long customerId, Integer points, String reference) {
        log.info("Customer {} redeeming {} points", customerId, points);
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        
        LoyaltyTransaction transaction = new LoyaltyTransaction();
        transaction.setCustomer(customer);
        transaction.setTransactionType("REDEEM");
        transaction.setPointsRedeemed(points);
        transaction.setPointsBalance(customer.getLoyaltyPoints() - points);
        transaction.setReferenceNumber(reference);
        transaction.setTransactionDate(LocalDateTime.now());
        
        loyaltyTransactionRepository.save(transaction);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Integer getAvailablePoints(Long customerId) {
        return loyaltyTransactionRepository.calculateAvailablePoints(customerId, LocalDate.now());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<LoyaltyTransaction> getTransactionHistory(Long customerId, Pageable pageable) {
        return loyaltyTransactionRepository.findByCustomerId(customerId, pageable);
    }
    
    @Override
    public void expirePoints() {
        log.info("Expiring loyalty points");
        // TODO: Implement point expiration logic
    }
}

