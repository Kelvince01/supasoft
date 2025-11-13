package com.supasoft.pricingservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Discount Management
 * Supports percentage and fixed amount discounts
 */
@Entity
@Table(name = "discounts", indexes = {
        @Index(name = "idx_discount_code", columnList = "code"),
        @Index(name = "idx_discount_dates", columnList = "start_date, end_date"),
        @Index(name = "idx_discount_status", columnList = "status"),
        @Index(name = "idx_discount_type", columnList = "discount_type")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Discount code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String code;
    
    @NotBlank(message = "Discount name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(length = 1000)
    private String description;
    
    @NotNull(message = "Discount type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false, length = 20)
    private DiscountType discountType;
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.00", message = "Discount value must be non-negative")
    @Column(name = "discount_value", nullable = false, precision = 19, scale = 2)
    private BigDecimal discountValue;
    
    @Column(name = "max_discount_amount", precision = 19, scale = 2)
    private BigDecimal maxDiscountAmount; // Maximum discount for percentage type
    
    @Column(name = "min_purchase_amount", precision = 19, scale = 2)
    private BigDecimal minPurchaseAmount; // Minimum purchase to qualify
    
    @Column(name = "max_purchase_amount", precision = 19, scale = 2)
    private BigDecimal maxPurchaseAmount; // Maximum purchase amount eligible
    
    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    
    @Column(name = "usage_limit")
    private Integer usageLimit; // Max times this discount can be used
    
    @Column(name = "usage_count")
    private Integer usageCount = 0; // Times this discount has been used
    
    @Column(name = "per_customer_limit")
    private Integer perCustomerLimit; // Max times per customer
    
    @Column(name = "is_cumulative")
    private Boolean isCumulative = false; // Can be combined with other discounts
    
    @Column(name = "applies_to_items")
    private Boolean appliesToItems = true; // Applies to specific items
    
    @Column(name = "applies_to_categories")
    private Boolean appliesToCategories = false; // Applies to categories
    
    @Column(name = "item_ids", length = 1000)
    private String itemIds; // Comma-separated item IDs
    
    @Column(name = "category_ids", length = 1000)
    private String categoryIds; // Comma-separated category IDs
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
    
    @Transient
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return status == Status.ACTIVE 
                && now.isAfter(startDate) 
                && now.isBefore(endDate)
                && (usageLimit == null || usageCount < usageLimit);
    }
}

