package com.supasoft.itemservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.supasoft.itemservice.entity.Category;

/**
 * Repository for Category entity
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Find category by code
     */
    Optional<Category> findByCategoryCode(String categoryCode);
    
    /**
     * Check if category code exists
     */
    boolean existsByCategoryCode(String categoryCode);
    
    /**
     * Find all active categories
     */
    @Query("SELECT c FROM Category c WHERE c.isActive = true AND c.isDeleted = false ORDER BY c.sortOrder, c.categoryName")
    List<Category> findAllActive();
    
    /**
     * Find root categories (categories without parent)
     */
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.isActive = true AND c.isDeleted = false ORDER BY c.sortOrder, c.categoryName")
    List<Category> findRootCategories();
    
    /**
     * Find child categories by parent ID
     */
    @Query("SELECT c FROM Category c WHERE c.parent.categoryId = :parentId AND c.isActive = true AND c.isDeleted = false ORDER BY c.sortOrder, c.categoryName")
    List<Category> findByParentId(@Param("parentId") Long parentId);
    
    /**
     * Find categories by level
     */
    @Query("SELECT c FROM Category c WHERE c.level = :level AND c.isActive = true AND c.isDeleted = false ORDER BY c.sortOrder, c.categoryName")
    List<Category> findByLevel(@Param("level") Integer level);
    
    /**
     * Search categories by name
     */
    @Query("SELECT c FROM Category c WHERE LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :search, '%')) AND c.isActive = true AND c.isDeleted = false")
    List<Category> searchByName(@Param("search") String search);
    
    /**
     * Count categories by parent
     */
    @Query("SELECT COUNT(c) FROM Category c WHERE c.parent.categoryId = :parentId AND c.isActive = true AND c.isDeleted = false")
    long countByParentId(@Param("parentId") Long parentId);
    
    /**
     * Count items in category
     */
    @Query("SELECT COUNT(i) FROM Item i WHERE i.category.categoryId = :categoryId AND i.isActive = true AND i.isDeleted = false")
    long countItemsInCategory(@Param("categoryId") Long categoryId);
}

