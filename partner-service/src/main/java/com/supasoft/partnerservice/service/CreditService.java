package com.supasoft.partnerservice.service;

import java.math.BigDecimal;

/**
 * Service interface for Credit management
 * TODO: Implement full credit management operations
 */
public interface CreditService {
    
    boolean validateCreditLimit(Long customerId, BigDecimal amount);
    
    void allocateCredit(Long customerId, BigDecimal amount);
    
    void releaseCredit(Long customerId, BigDecimal amount);
    
    BigDecimal getAvailableCredit(Long customerId);
    
    void updateCreditLimit(Long customerId, BigDecimal newLimit);
}

