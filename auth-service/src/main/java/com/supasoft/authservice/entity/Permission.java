package com.supasoft.authservice.entity;

import com.supasoft.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Permission entity representing system permissions
 */
@Entity
@Table(name = "permissions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;
    
    @Column(name = "permission_name", nullable = false, unique = true, length = 100)
    private String permissionName;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "resource", length = 100)
    private String resource;
    
    @Column(name = "action", length = 50)
    private String action;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
}

