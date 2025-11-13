//package com.supasoft.itemservice.repository;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//// Repository Example
//@Repository
//public interface ItemRepository extends JpaRepository<Item, Long>,
//        JpaSpecificationExecutor<Item> {
//
//    Optional<Item> findByItemCode(String itemCode);
//    Optional<Item> findByBarcode(String barcode);
//
//    @org.springframework.data.jpa.repository.Query("SELECT i FROM Item i WHERE i.category.categoryId = :categoryId " +
//            "AND i.isActive = true")
//    Page<Item> findActiveItemsByCategory(
//            @Param("categoryId") Long categoryId,
//            Pageable pageable
//    );
//
//    @Query("SELECT i FROM Item i WHERE " +
//            "LOWER(i.itemName) LIKE LOWER(CONCAT('%', :search, '%')) " +
//            "OR LOWER(i.barcode) LIKE LOWER(CONCAT('%', :search, '%'))")
//    Page<Item> searchItems(@Param("search") String search, Pageable pageable);
//}
