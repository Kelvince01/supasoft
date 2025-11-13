package com.supasoft.itemservice.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.supasoft.common.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Item entity representing products/items in the system
 */
@Entity
@Table(name = "items", indexes = {
    @Index(name = "idx_item_code", columnList = "item_code"),
    @Index(name = "idx_barcode", columnList = "barcode"),
    @Index(name = "idx_item_name", columnList = "item_name")
})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"category", "brand", "baseUom", "supplier", "barcodes", "uomConversions"})
@ToString(exclude = {"category", "brand", "baseUom", "supplier", "barcodes", "uomConversions"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    
    @Column(name = "item_code", nullable = false, unique = true, length = 50)
    private String itemCode;
    
    @Column(name = "barcode", unique = true, length = 50)
    private String barcode;
    
    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "short_description", length = 500)
    private String shortDescription;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_uom_id", nullable = false)
    private UnitOfMeasure baseUom;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    // Tax Information
    @Column(name = "vat_rate", precision = 5, scale = 2)
    private BigDecimal vatRate = new BigDecimal("16.00"); // Kenya VAT rate
    
    @Column(name = "is_vat_exempt")
    private Boolean isVatExempt = false;
    
    // Pricing (Base prices, actual pricing in pricing-service)
    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice = BigDecimal.ZERO;
    
    @Column(name = "selling_price", precision = 12, scale = 2)
    private BigDecimal sellingPrice = BigDecimal.ZERO;
    
    // Inventory Tracking
    @Column(name = "track_inventory")
    private Boolean trackInventory = true;
    
    @Column(name = "has_expiry")
    private Boolean hasExpiry = false;
    
    @Column(name = "shelf_life_days")
    private Integer shelfLifeDays;
    
    @Column(name = "reorder_level", precision = 10, scale = 2)
    private BigDecimal reorderLevel = BigDecimal.ZERO;
    
    @Column(name = "minimum_stock_level", precision = 10, scale = 2)
    private BigDecimal minimumStockLevel = BigDecimal.ZERO;
    
    @Column(name = "maximum_stock_level", precision = 10, scale = 2)
    private BigDecimal maximumStockLevel = BigDecimal.ZERO;
    
    // Physical Attributes
    @Column(name = "weight", precision = 10, scale = 3)
    private BigDecimal weight;
    
    @Column(name = "weight_unit", length = 10)
    private String weightUnit; // kg, g, lb
    
    @Column(name = "length", precision = 10, scale = 2)
    private BigDecimal length;
    
    @Column(name = "width", precision = 10, scale = 2)
    private BigDecimal width;
    
    @Column(name = "height", precision = 10, scale = 2)
    private BigDecimal height;
    
    @Column(name = "dimension_unit", length = 10)
    private String dimensionUnit; // cm, m, inch
    
    // Status & Flags
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "is_for_sale")
    private Boolean isForSale = true;
    
    @Column(name = "is_for_purchase")
    private Boolean isForPurchase = true;
    
    @Column(name = "is_serialized")
    private Boolean isSerialized = false;
    
    @Column(name = "is_batch_tracked")
    private Boolean isBatchTracked = false;
    
    // Additional Information
    @Column(name = "sku", unique = true, length = 100)
    private String sku;
    
    @Column(name = "manufacturer_part_number", length = 100)
    private String manufacturerPartNumber;
    
    @Column(name = "hsn_code", length = 20)
    private String hsnCode; // Harmonized System Nomenclature
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "tags", length = 500)
    private String tags;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "launch_date")
    private LocalDate launchDate;
    
    @Column(name = "discontinue_date")
    private LocalDate discontinueDate;
    
    // Relationships
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ItemBarcode> barcodes = new ArrayList<>();
    
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ItemUomConversion> uomConversions = new ArrayList<>();
    
    /**
     * Add barcode to item
     */
    public void addBarcode(ItemBarcode barcode) {
        barcodes.add(barcode);
        barcode.setItem(this);
    }
    
    /**
     * Add UOM conversion to item
     */
    public void addUomConversion(ItemUomConversion conversion) {
        uomConversions.add(conversion);
        conversion.setItem(this);
    }
    
    /**
     * Check if item is out of stock (for reorder alert)
     */
    public boolean isBelowReorderLevel(BigDecimal currentStock) {
        return reorderLevel != null && currentStock.compareTo(reorderLevel) < 0;
    }
}
