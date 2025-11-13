package com.supasoft.partnerservice.repository;

import com.supasoft.partnerservice.entity.Address;
import com.supasoft.partnerservice.enums.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Address entity
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    List<Address> findByCustomerId(Long customerId);
    
    List<Address> findBySupplierId(Long supplierId);
    
    List<Address> findByCustomerIdAndAddressType(Long customerId, AddressType addressType);
    
    List<Address> findBySupplierIdAndAddressType(Long supplierId, AddressType addressType);
    
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.isDefault = true")
    Optional<Address> findDefaultAddressByCustomer(@Param("customerId") Long customerId);
    
    @Query("SELECT a FROM Address a WHERE a.supplier.id = :supplierId AND a.isDefault = true")
    Optional<Address> findDefaultAddressBySupplier(@Param("supplierId") Long supplierId);
    
    @Query("SELECT a FROM Address a WHERE a.customer.id = :customerId AND a.addressType = :type AND a.isDefault = true")
    Optional<Address> findDefaultAddressByCustomerAndType(@Param("customerId") Long customerId, @Param("type") AddressType type);
    
    @Query("SELECT a FROM Address a WHERE a.supplier.id = :supplierId AND a.addressType = :type AND a.isDefault = true")
    Optional<Address> findDefaultAddressBySupplierAndType(@Param("supplierId") Long supplierId, @Param("type") AddressType type);
}

