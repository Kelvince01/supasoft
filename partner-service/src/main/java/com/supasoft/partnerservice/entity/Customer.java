package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.AuditableEntity;
import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.enums.LoyaltyTier;
import com.supasoft.partnerservice.enums.PaymentTerms;
import jakarta.persistence.*;
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
 * Entity for Customer
 * Main entity for customer management
 */
@Entity
@Table(name = "customers", indexes = {
        @Index(name = "idx_customer_code", columnList = "code"),
        @Index(name = "idx_customer_email", columnList = "email"),
        @Index(name = "idx_customer_phone", columnList = "phone"),
        @Index(name = "idx_customer_category", columnList = "category_id"),
        @Index(name = "idx_customer_status", columnList = "status"),
        @Index(name = "idx_customer_created", columnList = "created_at")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Customer code is required")
    @Size(max = 50, message = "Code must not exceed 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String code;
    
    @NotBlank(message = "Customer name is required")
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CustomerCategory category;
    
    @DecimalMin(value = "0.00", message = "Credit limit must be non-negative")
    @Column(name = "credit_limit", precision = 19, scale = 2)
    private BigDecimal creditLimit = BigDecimal.ZERO;
    
    @Column(name = "current_balance", precision = 19, scale = 2)
    private BigDecimal currentBalance = BigDecimal.ZERO;
    
    @Column(name = "available_credit", precision = 19, scale = 2)
    private BigDecimal availableCredit = BigDecimal.ZERO;
    
    @Column(name = "total_sales", precision = 19, scale = 2)
    private BigDecimal totalSales = BigDecimal.ZERO;
    
    @Column(name = "total_payments", precision = 19, scale = 2)
    private BigDecimal totalPayments = BigDecimal.ZERO;
    
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "loyalty_tier", length = 20)
    private LoyaltyTier loyaltyTier = LoyaltyTier.BRONZE;
    
    @Column(name = "total_points_earned")
    private Integer totalPointsEarned = 0;
    
    @Column(name = "total_points_redeemed")
    private Integer totalPointsRedeemed = 0;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_terms", length = 20)
    private PaymentTerms paymentTerms = PaymentTerms.COD;
    
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;
    
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    
    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "is_vip")
    private Boolean isVip = false;
    
    @Column(name = "is_credit_allowed")
    private Boolean isCreditAllowed = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactPerson> contacts = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    
    // Helper methods
    public void addContact(ContactPerson contact) {
        contacts.add(contact);
        contact.setCustomer(this);
    }
    
    public void removeContact(ContactPerson contact) {
        contacts.remove(contact);
        contact.setCustomer(null);
    }
    
    public void addAddress(Address address) {
        addresses.add(address);
        address.setCustomer(this);
    }
    
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setCustomer(null);
    }
    
    public void updateAvailableCredit() {
        if (creditLimit != null && currentBalance != null) {
            this.availableCredit = creditLimit.subtract(currentBalance);
        }
    }
}

