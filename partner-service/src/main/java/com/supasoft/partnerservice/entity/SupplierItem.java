package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity for Supplier-Item mapping
 * Links suppliers to items with specific terms
 */
@Entity
@Table(name = "supplier_items", indexes = {
        @Index(name = "idx_supplier_item_supplier", columnList = "supplier_id"),
        @Index(name = "idx_supplier_item_item", columnList = "item_id"),
        @Index(name = "idx_supplier_item_preferred", columnList = "is_preferred"),
        @Index(name = "idx_supplier_item_status", columnList = "status")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_supplier_item", columnNames = {"supplier_id", "item_id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SupplierItem extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Supplier is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @NotNull(message = "Item ID is required")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    
    @Size(max = 50, message = "Supplier item code must not exceed 50 characters")
    @Column(name = "supplier_item_code", length = 50)
    private String supplierItemCode;
    
    @DecimalMin(value = "0.00", message = "Cost price must be non-negative")
    @Column(name = "cost_price", precision = 19, scale = 2)
    private BigDecimal costPrice;
    
    @Min(value = 1, message = "Minimum order quantity must be at least 1")
    @Column(name = "min_order_quantity")
    private Integer minOrderQuantity = 1;
    
    @Column(name = "lead_time_days")
    private Integer leadTimeDays = 0;
    
    @Column(name = "is_preferred")
    private Boolean isPreferred = false;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    @Column(length = 500)
    private String notes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
}

