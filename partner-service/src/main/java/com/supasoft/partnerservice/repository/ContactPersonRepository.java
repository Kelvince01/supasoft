package com.supasoft.partnerservice.repository;

import com.supasoft.partnerservice.entity.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for ContactPerson entity
 */
@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {
    
    List<ContactPerson> findByCustomerId(Long customerId);
    
    List<ContactPerson> findBySupplierId(Long supplierId);
    
    @Query("SELECT cp FROM ContactPerson cp WHERE cp.customer.id = :customerId AND cp.isPrimary = true")
    Optional<ContactPerson> findPrimaryContactByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT cp FROM ContactPerson cp WHERE cp.supplier.id = :supplierId AND cp.isPrimary = true")
    Optional<ContactPerson> findPrimaryContactBySupplier(@Param("supplierId") Long supplierId);
    
    @Query("SELECT cp FROM ContactPerson cp WHERE cp.email = :email")
    Optional<ContactPerson> findByEmail(@Param("email") String email);
}

