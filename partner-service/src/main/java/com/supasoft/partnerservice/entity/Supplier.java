package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.enums.PaymentTerms;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Supplier
 * Main entity for supplier management
 */
@Entity
@Table(name = "suppliers", indexes = {
        @Index(name = "idx_supplier_code", columnList = "code"),
        @Index(name = "idx_supplier_email", columnList = "email"),
        @Index(name = "idx_supplier_phone", columnList = "phone"),
        @Index(name = "idx_supplier_rating", columnList = "rating"),
        @Index(name = "idx_supplier_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Supplier code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String code;
    
    @NotBlank(message = "Supplier name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String name;
    
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(length = 100)
    private String email;
    
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    @Column(length = 20)
    private String phone;
    
    @Size(max = 20, message = "Mobile must not exceed 20 characters")
    @Column(length = 20)
    private String mobile;
    
    @Size(max = 50, message = "Tax ID must not exceed 50 characters")
    @Column(name = "tax_id", length = 50)
    private String taxId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_terms", length = 20)
    private PaymentTerms paymentTerms = PaymentTerms.NET30;
    
    @Column(name = "lead_time_days")
    private Integer leadTimeDays = 0;
    
    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5")
    @Column(precision = 2, scale = 1)
    private BigDecimal rating = BigDecimal.ZERO;
    
    @Column(name = "total_purchases", precision = 19, scale = 2)
    private BigDecimal totalPurchases = BigDecimal.ZERO;
    
    @Column(name = "total_orders")
    private Integer totalOrders = 0;
    
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "is_preferred")
    private Boolean isPreferred = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactPerson> contacts = new ArrayList<>();
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierItem> supplierItems = new ArrayList<>();
    
    // Helper methods
    public void addContact(ContactPerson contact) {
        contacts.add(contact);
        contact.setSupplier(this);
    }
    
    public void removeContact(ContactPerson contact) {
        contacts.remove(contact);
        contact.setSupplier(null);
    }
    
    public void addAddress(Address address) {
        addresses.add(address);
        address.setSupplier(this);
    }
    
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setSupplier(null);
    }
    
    public void addSupplierItem(SupplierItem supplierItem) {
        supplierItems.add(supplierItem);
        supplierItem.setSupplier(this);
    }
    
    public void removeSupplierItem(SupplierItem supplierItem) {
        supplierItems.remove(supplierItem);
        supplierItem.setSupplier(null);
    }
}

