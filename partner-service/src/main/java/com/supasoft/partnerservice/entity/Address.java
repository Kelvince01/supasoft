package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.BaseEntity;
import com.supasoft.partnerservice.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity for Address
 * Linked to Customer or Supplier
 */
@Entity
@Table(name = "addresses", indexes = {
        @Index(name = "idx_address_customer", columnList = "customer_id"),
        @Index(name = "idx_address_supplier", columnList = "supplier_id"),
        @Index(name = "idx_address_type", columnList = "address_type"),
        @Index(name = "idx_address_default", columnList = "is_default")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 20)
    private AddressType addressType;
    
    @NotBlank(message = "Street is required")
    @Size(max = 200, message = "Street must not exceed 200 characters")
    @Column(nullable = false, length = 200)
    private String street;
    
    @Size(max = 100, message = "City must not exceed 100 characters")
    @Column(length = 100)
    private String city;
    
    @Size(max = 100, message = "State must not exceed 100 characters")
    @Column(length = 100)
    private String state;
    
    @Size(max = 100, message = "Country must not exceed 100 characters")
    @Column(length = 100)
    private String country;
    
    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    @Column(name = "postal_code", length = 20)
    private String postalCode;
    
    @Column(name = "is_default")
    private Boolean isDefault = false;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}

