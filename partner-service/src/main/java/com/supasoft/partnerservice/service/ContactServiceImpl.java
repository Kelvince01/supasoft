package com.supasoft.partnerservice.service;

import com.supasoft.partnerservice.entity.Address;
import com.supasoft.partnerservice.entity.ContactPerson;
import com.supasoft.partnerservice.repository.AddressRepository;
import com.supasoft.partnerservice.repository.ContactPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of ContactService
 * Basic implementation - extend as needed
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ContactServiceImpl implements ContactService {
    
    private final ContactPersonRepository contactPersonRepository;
    private final AddressRepository addressRepository;
    
    @Override
    public ContactPerson createContact(ContactPerson contact) {
        log.info("Creating new contact: {}", contact.getName());
        return contactPersonRepository.save(contact);
    }
    
    @Override
    public ContactPerson updateContact(Long id, ContactPerson contact) {
        log.info("Updating contact ID: {}", id);
        ContactPerson existing = contactPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        // TODO: Implement update logic
        return contactPersonRepository.save(existing);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ContactPerson getContactById(Long id) {
        return contactPersonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContactPerson> getContactsByCustomerId(Long customerId) {
        return contactPersonRepository.findByCustomerId(customerId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContactPerson> getContactsBySupplierId(Long supplierId) {
        return contactPersonRepository.findBySupplierId(supplierId);
    }
    
    @Override
    public void deleteContact(Long id) {
        log.info("Deleting contact ID: {}", id);
        contactPersonRepository.deleteById(id);
    }
    
    @Override
    public Address createAddress(Address address) {
        log.info("Creating new address");
        return addressRepository.save(address);
    }
    
    @Override
    public Address updateAddress(Long id, Address address) {
        log.info("Updating address ID: {}", id);
        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        // TODO: Implement update logic
        return addressRepository.save(existing);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Address> getAddressesByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Address> getAddressesBySupplierId(Long supplierId) {
        return addressRepository.findBySupplierId(supplierId);
    }
    
    @Override
    public void deleteAddress(Long id) {
        log.info("Deleting address ID: {}", id);
        addressRepository.deleteById(id);
    }
}

