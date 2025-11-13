package com.supasoft.itemservice.service;

import java.util.List;

import com.supasoft.itemservice.dto.request.CreateCategoryRequest;
import com.supasoft.itemservice.dto.response.CategoryResponse;

/**
 * Service interface for Category operations
 */
public interface CategoryService {
    
    /**
     * Create a new category
     */
    CategoryResponse createCategory(CreateCategoryRequest request);
    
    /**
     * Update an existing category
     */
    CategoryResponse updateCategory(Long categoryId, CreateCategoryRequest request);
    
    /**
     * Get category by ID
     */
    CategoryResponse getCategoryById(Long categoryId);
    
    /**
     * Get category by code
     */
    CategoryResponse getCategoryByCode(String categoryCode);
    
    /**
     * Get all active categories
     */
    List<CategoryResponse> getAllActiveCategories();
    
    /**
     * Get root categories (categories without parent)
     */
    List<CategoryResponse> getRootCategories();
    
    /**
     * Get child categories by parent ID
     */
    List<CategoryResponse> getChildCategories(Long parentId);
    
    /**
     * Get categories by level
     */
    List<CategoryResponse> getCategoriesByLevel(Integer level);
    
    /**
     * Search categories by name
     */
    List<CategoryResponse> searchCategories(String searchTerm);
    
    /**
     * Delete category (soft delete)
     */
    void deleteCategory(Long categoryId);
    
    /**
     * Get category hierarchy tree
     */
    List<CategoryResponse> getCategoryHierarchy();
}

