package com.supasoft.pricingservice.entity;

import com.supasoft.common.entity.BaseEntity;
import com.supasoft.common.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity for Price Types (e.g., Retail, Wholesale, Distributor)
 * Supports multi-tier pricing strategy
 */
@Entity
@Table(name = "price_types", indexes = {
        @Index(name = "idx_price_type_code", columnList = "code"),
        @Index(name = "idx_price_type_status", columnList = "status")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceType extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Price type code is required")
    @Size(max = 20, message = "Code must not exceed 20 characters")
    @Column(nullable = false, unique = true, length = 20)
    private String code;
    
    @NotBlank(message = "Price type name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private Integer priority = 0; // Lower number = higher priority
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
    
    @Column(name = "is_default")
    private Boolean isDefault = false;
}

