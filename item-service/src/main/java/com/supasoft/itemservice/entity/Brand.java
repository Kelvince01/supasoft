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
 * Brand entity representing product brands
 */
@Entity
@Table(name = "brands")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "items")
@ToString(exclude = "items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;
    
    @Column(name = "brand_code", nullable = false, unique = true, length = 50)
    private String brandCode;
    
    @Column(name = "brand_name", nullable = false, length = 100)
    private String brandName;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
    
    @Column(name = "country_of_origin", length = 50)
    private String countryOfOrigin;
    
    @Column(name = "logo_url", length = 255)
    private String logoUrl;
    
    @Column(name = "website", length = 255)
    private String website;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}

