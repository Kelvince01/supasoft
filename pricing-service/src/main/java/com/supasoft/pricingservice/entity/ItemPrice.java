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
 * Entity for Item Prices
 * Links items with price types and stores pricing details
 */
@Entity
@Table(name = "item_prices", indexes = {
        @Index(name = "idx_item_price_item", columnList = "item_id"),
        @Index(name = "idx_item_price_type", columnList = "price_type_id"),
        @Index(name = "idx_item_price_dates", columnList = "effective_date, expiry_date"),
        @Index(name = "idx_item_price_status", columnList = "status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_item_price_type", columnNames = {"item_id", "price_type_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ItemPrice extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Item ID is required")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @NotNull(message = "Price type is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_type_id", nullable = false)
    private PriceType priceType;
    
    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.01", message = "Selling price must be greater than 0")
    @Column(name = "selling_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal sellingPrice;
    
    @NotNull(message = "Cost price is required")
    @DecimalMin(value = "0.00", message = "Cost price must be non-negative")
    @Column(name = "cost_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal costPrice;
    
    @Column(name = "profit_margin", precision = 10, scale = 2)
    private BigDecimal profitMargin; // Calculated as percentage
    
    @Column(name = "profit_amount", precision = 19, scale = 2)
    private BigDecimal profitAmount; // Calculated profit
    
    @DecimalMin(value = "0.00", message = "Min price must be non-negative")
    @Column(name = "min_price", precision = 19, scale = 2)
    private BigDecimal minPrice; // Minimum allowed selling price
    
    @DecimalMin(value = "0.00", message = "Max price must be non-negative")
    @Column(name = "max_price", precision = 19, scale = 2)
    private BigDecimal maxPrice; // Maximum allowed selling price
    
    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;
    
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
    
    @Column(name = "is_taxable")
    private Boolean isTaxable = true;
    
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate = BigDecimal.ZERO;
    
    @PrePersist
    @PreUpdate
    private void calculateProfitMargin() {
        if (sellingPrice != null && costPrice != null && costPrice.compareTo(BigDecimal.ZERO) > 0) {
            profitAmount = sellingPrice.subtract(costPrice);
            profitMargin = profitAmount.divide(costPrice, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
    }
}

