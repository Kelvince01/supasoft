package com.supasoft.itemservice.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Item Barcode entity - supports multiple barcodes per item
 */
@Entity
@Table(name = "item_barcodes")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "item")
@ToString(exclude = "item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemBarcode extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barcode_id")
    private Long barcodeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @Column(name = "barcode", nullable = false, unique = true, length = 50)
    private String barcode;
    
    @Column(name = "barcode_type", length = 20)
    private String barcodeType; // EAN13, CODE128, QR, etc.
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "description", length = 255)
    private String description;
}

