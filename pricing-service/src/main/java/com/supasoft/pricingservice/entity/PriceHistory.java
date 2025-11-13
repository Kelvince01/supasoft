package com.supasoft.pricingservice.entity;

import com.supasoft.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity for Price History
 * Tracks all price changes for audit and analysis purposes
 */
@Entity
@Table(name = "price_history", indexes = {
        @Index(name = "idx_price_history_item", columnList = "item_id"),
        @Index(name = "idx_price_history_price_type", columnList = "price_type_id"),
        @Index(name = "idx_price_history_date", columnList = "changed_at"),
        @Index(name = "idx_price_history_user", columnList = "changed_by")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceHistory extends BaseEntity {
    
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
    
    @NotNull(message = "Old selling price is required")
    @Column(name = "old_selling_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal oldSellingPrice;
    
    @NotNull(message = "New selling price is required")
    @Column(name = "new_selling_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal newSellingPrice;
    
    @NotNull(message = "Old cost price is required")
    @Column(name = "old_cost_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal oldCostPrice;
    
    @NotNull(message = "New cost price is required")
    @Column(name = "new_cost_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal newCostPrice;
    
    @Column(name = "old_profit_margin", precision = 10, scale = 2)
    private BigDecimal oldProfitMargin;
    
    @Column(name = "new_profit_margin", precision = 10, scale = 2)
    private BigDecimal newProfitMargin;
    
    @NotNull(message = "Changed at is required")
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
    
    @NotNull(message = "Changed by is required")
    @Column(name = "changed_by", nullable = false, length = 100)
    private String changedBy;
    
    @Column(name = "change_reason", length = 500)
    private String changeReason;
    
    @Column(name = "approved_by", length = 100)
    private String approvedBy;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}

