package com.supasoft.pricingservice.entity;

import com.supasoft.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity for Promotion Items
 * Links items to promotions with role-based configurations
 */
@Entity
@Table(name = "promotion_items", indexes = {
        @Index(name = "idx_promotion_item_promotion", columnList = "promotion_id"),
        @Index(name = "idx_promotion_item_item", columnList = "item_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_promotion_item", columnNames = {"promotion_id", "item_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PromotionItem extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Promotion is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;
    
    @NotNull(message = "Item ID is required")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @NotNull(message = "Item role is required")
    @Column(name = "item_role", nullable = false, length = 20)
    private String itemRole; // BUY, GET, BUNDLE
    
    @Column(name = "required_quantity")
    private Integer requiredQuantity;
    
    @DecimalMin(value = "0.00", message = "Special price must be non-negative")
    @Column(name = "special_price", precision = 19, scale = 2)
    private BigDecimal specialPrice;
    
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;
    
    @Column(name = "is_required")
    private Boolean isRequired = true;
}

