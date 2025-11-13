package com.supasoft.pricingservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import com.supasoft.pricingservice.enums.PromotionType;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Promotion Management
 * Supports various promotion types: BOGO, Buy X Get Y, Bundle, etc.
 */
@Entity
@Table(name = "promotions", indexes = {
        @Index(name = "idx_promotion_code", columnList = "code"),
        @Index(name = "idx_promotion_dates", columnList = "start_date, end_date"),
        @Index(name = "idx_promotion_status", columnList = "status"),
        @Index(name = "idx_promotion_type", columnList = "promotion_type")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Promotion extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Promotion code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String code;
    
    @NotBlank(message = "Promotion name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(length = 1000)
    private String description;
    
    @NotNull(message = "Promotion type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_type", nullable = false, length = 30)
    private PromotionType promotionType;
    
    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    
    // Buy X Get Y configuration
    @Column(name = "buy_quantity")
    private Integer buyQuantity; // Buy X items
    
    @Column(name = "get_quantity")
    private Integer getQuantity; // Get Y items
    
    @Column(name = "get_discount_percentage", precision = 5, scale = 2)
    private BigDecimal getDiscountPercentage; // Discount % on Y items
    
    // Bundle pricing
    @Column(name = "bundle_price", precision = 19, scale = 2)
    private BigDecimal bundlePrice; // Fixed price for bundle
    
    @DecimalMin(value = "0.00", message = "Discount must be non-negative")
    @Column(name = "discount_amount", precision = 19, scale = 2)
    private BigDecimal discountAmount;
    
    @DecimalMin(value = "0.00", message = "Discount percentage must be non-negative")
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @Column(name = "min_purchase_amount", precision = 19, scale = 2)
    private BigDecimal minPurchaseAmount;
    
    @Column(name = "max_discount_amount", precision = 19, scale = 2)
    private BigDecimal maxDiscountAmount;
    
    @Column(name = "usage_limit")
    private Integer usageLimit;
    
    @Column(name = "usage_count")
    private Integer usageCount = 0;
    
    @Column(name = "per_customer_limit")
    private Integer perCustomerLimit;
    
    @Column(name = "priority")
    private Integer priority = 0; // Lower number = higher priority
    
    @Column(name = "is_cumulative")
    private Boolean isCumulative = false;
    
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionItem> promotionItems = new ArrayList<>();
    
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
    
    public void addPromotionItem(PromotionItem item) {
        promotionItems.add(item);
        item.setPromotion(this);
    }
    
    public void removePromotionItem(PromotionItem item) {
        promotionItems.remove(item);
        item.setPromotion(null);
    }
}

