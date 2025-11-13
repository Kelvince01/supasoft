package com.supasoft.authservice.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supasoft.authservice.entity.Permission;

/**
 * Repository interface for Permission entity
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    /**
     * Find permission by permission name
     */
    Optional<Permission> findByPermissionName(String permissionName);
    
    /**
     * Check if permission exists by name
     */
    boolean existsByPermissionName(String permissionName);
    
    /**
     * Find all active permissions
     */
    @Query("SELECT p FROM Permission p WHERE p.isActive = true AND p.isDeleted = false")
    Set<Permission> findAllActive();
    
    /**
     * Find permissions by resource
     */
    Set<Permission> findByResource(String resource);
    
    /**
     * Find permissions by action
     */
    Set<Permission> findByAction(String action);
}

