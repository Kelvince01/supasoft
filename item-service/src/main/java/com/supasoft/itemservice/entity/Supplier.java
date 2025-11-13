package com.supasoft.itemservice.entity;

import java.math.BigDecimal;
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
 * Supplier entity representing vendors/suppliers
 */
@Entity
@Table(name = "suppliers")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "items")
@ToString(exclude = "items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;
    
    @Column(name = "supplier_code", nullable = false, unique = true, length = 50)
    private String supplierCode;
    
    @Column(name = "supplier_name", nullable = false, length = 200)
    private String supplierName;
    
    @Column(name = "contact_person", length = 100)
    private String contactPerson;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;
    
    @Column(name = "address", length = 500)
    private String address;
    
    @Column(name = "city", length = 100)
    private String city;
    
    @Column(name = "country", length = 50)
    private String country = "Kenya";
    
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    
    // Tax Information
    @Column(name = "tax_id", length = 50)
    private String taxId; // KRA PIN in Kenya
    
    @Column(name = "vat_number", length = 50)
    private String vatNumber;
    
    // Business Terms
    @Column(name = "payment_terms", length = 100)
    private String paymentTerms; // Net 30, Net 60, etc.
    
    @Column(name = "credit_limit", precision = 15, scale = 2)
    private BigDecimal creditLimit = BigDecimal.ZERO;
    
    @Column(name = "credit_days")
    private Integer creditDays = 0;
    
    @Column(name = "lead_time_days")
    private Integer leadTimeDays = 0;
    
    @Column(name = "minimum_order_value", precision = 12, scale = 2)
    private BigDecimal minimumOrderValue = BigDecimal.ZERO;
    
    // Rating & Status
    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "is_preferred")
    private Boolean isPreferred = false;
    
    // Bank Details
    @Column(name = "bank_name", length = 100)
    private String bankName;
    
    @Column(name = "bank_account_number", length = 50)
    private String bankAccountNumber;
    
    @Column(name = "bank_branch", length = 100)
    private String bankBranch;
    
    @Column(name = "swift_code", length = 20)
    private String swiftCode;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}

