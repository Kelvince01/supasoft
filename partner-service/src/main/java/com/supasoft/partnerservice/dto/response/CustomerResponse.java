package com.supasoft.partnerservice.dto.response;

import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.enums.LoyaltyTier;
import com.supasoft.partnerservice.enums.PaymentTerms;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for customer data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    
    private Long id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String mobile;
    private String taxId;
    private String categoryName;
    private Long categoryId;
    private BigDecimal creditLimit;
    private BigDecimal currentBalance;
    private BigDecimal availableCredit;
    private BigDecimal totalSales;
    private BigDecimal totalPayments;
    private Integer loyaltyPoints;
    private LoyaltyTier loyaltyTier;
    private Integer totalPointsEarned;
    private Integer totalPointsRedeemed;
    private PaymentTerms paymentTerms;
    private BigDecimal discountPercentage;
    private LocalDate registrationDate;
    private LocalDate lastPurchaseDate;
    private String notes;
    private Boolean isVip;
    private Boolean isCreditAllowed;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

