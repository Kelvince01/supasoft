package com.supasoft.itemservice.entity;

import java.util.ArrayList;
import java.util.List;

import com.supasoft.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Unit of Measure entity (pieces, kg, liters, cartons, etc.)
 */
@Entity
@Table(name = "units_of_measure")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"items", "conversions"})
@ToString(exclude = {"items", "conversions"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitOfMeasure extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uom_id")
    private Long uomId;
    
    @Column(name = "uom_code", nullable = false, unique = true, length = 20)
    private String uomCode;
    
    @Column(name = "uom_name", nullable = false, length = 50)
    private String uomName;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "uom_type", length = 20)
    private String uomType; // WEIGHT, VOLUME, COUNT, LENGTH
    
    @Column(name = "symbol", length = 10)
    private String symbol; // kg, L, pcs, m
    
    @Column(name = "is_base_unit")
    private Boolean isBaseUnit = false;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "baseUom", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
    
    @OneToMany(mappedBy = "fromUom", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ItemUomConversion> conversions = new ArrayList<>();
}

