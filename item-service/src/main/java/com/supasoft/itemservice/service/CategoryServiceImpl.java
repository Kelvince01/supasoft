package com.supasoft.itemservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supasoft.common.constant.MessageConstants;
import com.supasoft.common.exception.BusinessException;
import com.supasoft.common.exception.ResourceNotFoundException;
import com.supasoft.itemservice.dto.request.CreateCategoryRequest;
import com.supasoft.itemservice.dto.response.CategoryResponse;
import com.supasoft.itemservice.entity.Category;
import com.supasoft.itemservice.mapper.CategoryMapper;
import com.supasoft.itemservice.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Category operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        log.info("Creating category with code: {}", request.getCategoryCode());
        
        // Check if category code already exists
        if (categoryRepository.existsByCategoryCode(request.getCategoryCode())) {
            throw new BusinessException(String.format("Category with code '%s' already exists", request.getCategoryCode()));
        }
        
        Category category = categoryMapper.toEntity(request);
        
        // Set parent if provided
        if (request.getParentCategoryId() != null) {
            Category parent = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getParentCategoryId()));
            category.setParent(parent);
            category.setLevel(parent.getLevel() + 1);
        } else {
            category.setLevel(0);
        }
        
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created successfully with ID: {}", savedCategory.getCategoryId());
        
        CategoryResponse response = categoryMapper.toResponse(savedCategory);
        response.setItemCount(0L);
        response.setChildCount(0L);
        
        return response;
    }
    
    @Override
    public CategoryResponse updateCategory(Long categoryId, CreateCategoryRequest request) {
        log.info("Updating category with ID: {}", categoryId);
        
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        
        // Check if new code conflicts with existing categories
        if (!category.getCategoryCode().equals(request.getCategoryCode()) &&
            categoryRepository.existsByCategoryCode(request.getCategoryCode())) {
            throw new BusinessException(String.format("Category with code '%s' already exists", request.getCategoryCode()));
        }
        
        categoryMapper.updateEntity(request, category);
        
        // Update parent if changed
        if (request.getParentCategoryId() != null) {
            if (request.getParentCategoryId().equals(categoryId)) {
                throw new BusinessException("Category cannot be its own parent");
            }
            Category parent = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getParentCategoryId()));
            category.setParent(parent);
            category.setLevel(parent.getLevel() + 1);
        } else {
            category.setParent(null);
            category.setLevel(0);
        }
        
        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated successfully: {}", updatedCategory.getCategoryId());
        
        return enrichCategoryResponse(categoryMapper.toResponse(updatedCategory));
    }
    
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return enrichCategoryResponse(categoryMapper.toResponse(category));
    }
    
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryByCode(String categoryCode) {
        Category category = categoryRepository.findByCategoryCode(categoryCode)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "code", categoryCode));
        return enrichCategoryResponse(categoryMapper.toResponse(category));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllActiveCategories() {
        return categoryRepository.findAllActive().stream()
                .map(categoryMapper::toResponse)
                .map(this::enrichCategoryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findRootCategories().stream()
                .map(categoryMapper::toResponse)
                .map(this::enrichCategoryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getChildCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
                .map(categoryMapper::toResponse)
                .map(this::enrichCategoryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoriesByLevel(Integer level) {
        return categoryRepository.findByLevel(level).stream()
                .map(categoryMapper::toResponse)
                .map(this::enrichCategoryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> searchCategories(String searchTerm) {
        return categoryRepository.searchByName(searchTerm).stream()
                .map(categoryMapper::toResponse)
                .map(this::enrichCategoryResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteCategory(Long categoryId) {
        log.info("Deleting category with ID: {}", categoryId);
        
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        
        // Check if category has children
        long childCount = categoryRepository.countByParentId(categoryId);
        if (childCount > 0) {
            throw new BusinessException("Cannot delete category with child categories. Please delete children first.");
        }
        
        // Check if category has items
        long itemCount = categoryRepository.countItemsInCategory(categoryId);
        if (itemCount > 0) {
            throw new BusinessException(String.format("Cannot delete category with %d items. Please reassign items first.", itemCount));
        }
        
        category.setIsActive(false);
        categoryRepository.save(category);
        log.info("Category deleted successfully: {}", categoryId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryHierarchy() {
        List<Category> rootCategories = categoryRepository.findRootCategories();
        return rootCategories.stream()
                .map(this::buildCategoryTree)
                .collect(Collectors.toList());
    }
    
    /**
     * Build category tree recursively
     */
    private CategoryResponse buildCategoryTree(Category category) {
        CategoryResponse response = categoryMapper.toResponse(category);
        response.setItemCount(categoryRepository.countItemsInCategory(category.getCategoryId()));
        
        List<Category> children = categoryRepository.findByParentId(category.getCategoryId());
        if (!children.isEmpty()) {
            List<CategoryResponse> childResponses = children.stream()
                    .map(this::buildCategoryTree)
                    .collect(Collectors.toList());
            response.setChildren(childResponses);
            response.setChildCount((long) children.size());
        } else {
            response.setChildren(new ArrayList<>());
            response.setChildCount(0L);
        }
        
        return response;
    }
    
    /**
     * Enrich category response with counts
     */
    private CategoryResponse enrichCategoryResponse(CategoryResponse response) {
        response.setItemCount(categoryRepository.countItemsInCategory(response.getCategoryId()));
        response.setChildCount(categoryRepository.countByParentId(response.getCategoryId()));
        return response;
    }
}

