package com.supasoft.partnerservice.entity;

import com.supasoft.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity for Contact Person
 * Linked to Customer or Supplier
 */
@Entity
@Table(name = "contact_persons", indexes = {
        @Index(name = "idx_contact_customer", columnList = "customer_id"),
        @Index(name = "idx_contact_supplier", columnList = "supplier_id"),
        @Index(name = "idx_contact_primary", columnList = "is_primary")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ContactPerson extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @NotBlank(message = "Contact name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 100, message = "Position must not exceed 100 characters")
    @Column(length = 100)
    private String position;
    
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
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}

