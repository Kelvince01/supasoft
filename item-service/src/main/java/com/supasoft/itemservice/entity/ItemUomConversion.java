package com.supasoft.itemservice.entity;

import java.math.BigDecimal;

import com.supasoft.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * UOM Conversion entity (e.g., 1 carton = 24 pieces, 1 dozen = 12 pieces)
 */
@Entity
@Table(name = "item_uom_conversions", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "from_uom_id", "to_uom_id"}))
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"item", "fromUom", "toUom"})
@ToString(exclude = {"item", "fromUom", "toUom"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemUomConversion extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversion_id")
    private Long conversionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_uom_id", nullable = false)
    private UnitOfMeasure fromUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_uom_id", nullable = false)
    private UnitOfMeasure toUom;
    
    @Column(name = "conversion_factor", nullable = false, precision = 10, scale = 4)
    private BigDecimal conversionFactor; // 1 from_uom = conversion_factor * to_uom
    
    @Column(name = "barcode", unique = true, length = 50)
    private String barcode; // Optional barcode for this UOM
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    /**
     * Convert quantity from one UOM to another
     */
    public BigDecimal convert(BigDecimal quantity) {
        return quantity.multiply(conversionFactor);
    }
    
    /**
     * Convert quantity from another UOM to this one (reverse)
     */
    public BigDecimal reverseConvert(BigDecimal quantity) {
        return quantity.divide(conversionFactor, 4, BigDecimal.ROUND_HALF_UP);
    }
}

