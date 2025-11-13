package com.supasoft.pricingservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Customer-Specific Pricing
 * Allows setting custom prices for specific customers
 */
@Entity
@Table(name = "customer_pricing", indexes = {
        @Index(name = "idx_customer_pricing_customer", columnList = "customer_id"),
        @Index(name = "idx_customer_pricing_item", columnList = "item_id"),
        @Index(name = "idx_customer_pricing_dates", columnList = "effective_date, expiry_date"),
        @Index(name = "idx_customer_pricing_status", columnList = "status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_customer_item", columnNames = {"customer_id", "item_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPricing extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Customer ID is required")
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    @NotNull(message = "Item ID is required")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @NotNull(message = "Special price is required")
    @DecimalMin(value = "0.01", message = "Special price must be greater than 0")
    @Column(name = "special_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal specialPrice;
    
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @Column(name = "min_order_quantity")
    private Integer minOrderQuantity;
    
    @Column(name = "max_order_quantity")
    private Integer maxOrderQuantity;
    
    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;
    
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    @Transient
    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return status == Status.ACTIVE 
                && (effectiveDate == null || now.isAfter(effectiveDate))
                && (expiryDate == null || now.isBefore(expiryDate));
    }
}

