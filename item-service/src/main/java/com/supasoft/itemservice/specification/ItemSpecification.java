package com.supasoft.itemservice.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.supasoft.itemservice.entity.Item;

import jakarta.persistence.criteria.Predicate;

/**
 * JPA Specifications for dynamic Item queries
 */
public class ItemSpecification {
    
    /**
     * Build specification for item search with multiple criteria
     */
    public static Specification<Item> buildSearchSpecification(
            String search,
            Long categoryId,
            Long brandId,
            Long supplierId,
            Boolean isActive,
            Boolean isForSale,
            Boolean isForPurchase,
            Boolean trackInventory
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Always filter out deleted items
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
            
            // Search term (item name, code, or barcode)
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";
                Predicate searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("itemName")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("itemCode")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("barcode")), searchPattern)
                );
                predicates.add(searchPredicate);
            }
            
            // Category filter
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId));
            }
            
            // Brand filter
            if (brandId != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand").get("brandId"), brandId));
            }
            
            // Supplier filter
            if (supplierId != null) {
                predicates.add(criteriaBuilder.equal(root.get("supplier").get("supplierId"), supplierId));
            }
            
            // Active status filter
            if (isActive != null) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), isActive));
            }
            
            // For sale filter
            if (isForSale != null) {
                predicates.add(criteriaBuilder.equal(root.get("isForSale"), isForSale));
            }
            
            // For purchase filter
            if (isForPurchase != null) {
                predicates.add(criteriaBuilder.equal(root.get("isForPurchase"), isForPurchase));
            }
            
            // Track inventory filter
            if (trackInventory != null) {
                predicates.add(criteriaBuilder.equal(root.get("trackInventory"), trackInventory));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
    /**
     * Specification for active items only
     */
    public static Specification<Item> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isActive"), true),
                        criteriaBuilder.equal(root.get("isDeleted"), false)
                );
    }
    
    /**
     * Specification for items for sale
     */
    public static Specification<Item> isForSale() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("isForSale"), true),
                        criteriaBuilder.equal(root.get("isActive"), true),
                        criteriaBuilder.equal(root.get("isDeleted"), false)
                );
    }
    
    /**
     * Specification for items by category
     */
    public static Specification<Item> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
    }
    
    /**
     * Specification for items by brand
     */
    public static Specification<Item> hasBrandId(Long brandId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("brand").get("brandId"), brandId);
    }
}

