package com.supasoft.partnerservice.dto.request;

import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.enums.LoyaltyTier;
import com.supasoft.partnerservice.enums.PaymentTerms;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO for updating an existing customer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {
    
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;
    
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;
    
    @Size(max = 20, message = "Mobile must not exceed 20 characters")
    private String mobile;
    
    @Size(max = 50, message = "Tax ID must not exceed 50 characters")
    private String taxId;
    
    private Long categoryId;
    
    @DecimalMin(value = "0.00", message = "Credit limit must be non-negative")
    private BigDecimal creditLimit;
    
    private LoyaltyTier loyaltyTier;
    
    private PaymentTerms paymentTerms;
    
    private BigDecimal discountPercentage;
    
    private String notes;
    
    private Boolean isVip;
    
    private Boolean isCreditAllowed;
    
    private Status status;
}

