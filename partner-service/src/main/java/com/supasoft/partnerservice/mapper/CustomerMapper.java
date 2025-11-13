package com.supasoft.partnerservice.mapper;

import com.supasoft.partnerservice.dto.request.CreateCustomerRequest;
import com.supasoft.partnerservice.dto.request.UpdateCustomerRequest;
import com.supasoft.partnerservice.dto.response.CustomerResponse;
import com.supasoft.partnerservice.entity.Customer;
import org.mapstruct.*;

import java.time.LocalDate;

/**
 * MapStruct mapper for Customer entity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "currentBalance", constant = "0")
    @Mapping(target = "availableCredit", constant = "0")
    @Mapping(target = "totalSales", constant = "0")
    @Mapping(target = "totalPayments", constant = "0")
    @Mapping(target = "loyaltyPoints", constant = "0")
    @Mapping(target = "totalPointsEarned", constant = "0")
    @Mapping(target = "totalPointsRedeemed", constant = "0")
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    Customer toEntity(CreateCustomerRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "currentBalance", ignore = true)
    @Mapping(target = "availableCredit", ignore = true)
    @Mapping(target = "totalSales", ignore = true)
    @Mapping(target = "totalPayments", ignore = true)
    @Mapping(target = "loyaltyPoints", ignore = true)
    @Mapping(target = "totalPointsEarned", ignore = true)
    @Mapping(target = "totalPointsRedeemed", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "lastPurchaseDate", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateCustomerRequest request, @MappingTarget Customer customer);
    
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    CustomerResponse toResponse(Customer customer);
}

