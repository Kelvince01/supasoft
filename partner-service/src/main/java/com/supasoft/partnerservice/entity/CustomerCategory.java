package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.enums.LoyaltyTier;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity for Customer Categories
 * Defines customer types with default settings
 */
@Entity
@Table(name = "customer_categories", indexes = {
        @Index(name = "idx_customer_category_code", columnList = "code"),
        @Index(name = "idx_customer_category_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCategory extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Category code is required")
    @Size(max = 20, message = "Code must not exceed 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String code;
    
    @NotBlank(message = "Category name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(length = 500)
    private String description;
    
    @DecimalMin(value = "0.00", message = "Default credit limit must be non-negative")
    @Column(name = "default_credit_limit", precision = 19, scale = 2)
    private BigDecimal defaultCreditLimit;
    
    @DecimalMin(value = "0.00", message = "Default discount must be non-negative")
    @DecimalMax(value = "100.00", message = "Default discount cannot exceed 100%")
    @Column(name = "default_discount_percentage", precision = 5, scale = 2)
    private BigDecimal defaultDiscountPercentage = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty_tier", length = 20)
    private LoyaltyTier loyaltyTier = LoyaltyTier.BRONZE;
    
    @Column(name = "loyalty_multiplier", precision = 5, scale = 2)
    private BigDecimal loyaltyMultiplier = BigDecimal.ONE;
    
    @Column(name = "points_per_currency", precision = 5, scale = 2)
    private BigDecimal pointsPerCurrency = BigDecimal.ONE;
    
    @Column(name = "grace_period_days")
    private Integer gracePeriodDays = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
}

