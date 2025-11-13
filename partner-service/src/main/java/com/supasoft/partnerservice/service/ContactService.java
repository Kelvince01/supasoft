package com.supasoft.partnerservice.service;

import com.supasoft.partnerservice.entity.Address;
import com.supasoft.partnerservice.entity.ContactPerson;

import java.util.List;

/**
 * Service interface for Contact and Address management
 * TODO: Implement full contact management operations
 */
public interface ContactService {
    
    ContactPerson createContact(ContactPerson contact);
    
    ContactPerson updateContact(Long id, ContactPerson contact);
    
    ContactPerson getContactById(Long id);
    
    List<ContactPerson> getContactsByCustomerId(Long customerId);
    
    List<ContactPerson> getContactsBySupplierId(Long supplierId);
    
    void deleteContact(Long id);
    
    Address createAddress(Address address);
    
    Address updateAddress(Long id, Address address);
    
    Address getAddressById(Long id);
    
    List<Address> getAddressesByCustomerId(Long customerId);
    
    List<Address> getAddressesBySupplierId(Long supplierId);
    
    void deleteAddress(Long id);
}

