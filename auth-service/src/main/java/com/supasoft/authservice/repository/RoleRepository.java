package com.supasoft.authservice.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.authservice.entity.Role;

/**
 * Repository interface for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find role by role name
     */
    Optional<Role> findByRoleName(String roleName);
    
    /**
     * Check if role exists by name
     */
    boolean existsByRoleName(String roleName);
    
    /**
     * Find all active roles
     */
    @Query("SELECT r FROM Role r WHERE r.isActive = true AND r.isDeleted = false")
    Set<Role> findAllActive();
    
    /**
     * Find roles by names
     */
    @Query("SELECT r FROM Role r WHERE r.roleName IN :roleNames")
    Set<Role> findByRoleNameIn(@Param("roleNames") Set<String> roleNames);
}

