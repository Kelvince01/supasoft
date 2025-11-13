package com.supasoft.itemservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supasoft.common.dto.ApiResponse;
import com.supasoft.itemservice.dto.request.CreateCategoryRequest;
import com.supasoft.itemservice.dto.response.CategoryResponse;
import com.supasoft.itemservice.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for Category operations
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "APIs for managing product categories")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('CREATE_ITEMS')")
    @Operation(summary = "Create a new category")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Category created successfully", response));
    }
    
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('UPDATE_ITEMS')")
    @Operation(summary = "Update a category")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", response));
    }
    
    @GetMapping("/{categoryId}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponse response = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", response));
    }
    
    @GetMapping("/code/{categoryCode}")
    @Operation(summary = "Get category by code")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByCode(@PathVariable String categoryCode) {
        CategoryResponse response = categoryService.getCategoryByCode(categoryCode);
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", response));
    }
    
    @GetMapping
    @Operation(summary = "Get all active categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllActiveCategories();
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", categories));
    }
    
    @GetMapping("/root")
    @Operation(summary = "Get root categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getRootCategories() {
        List<CategoryResponse> categories = categoryService.getRootCategories();
        return ResponseEntity.ok(ApiResponse.success("Root categories retrieved successfully", categories));
    }
    
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Get child categories by parent ID")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getChildCategories(@PathVariable Long parentId) {
        List<CategoryResponse> categories = categoryService.getChildCategories(parentId);
        return ResponseEntity.ok(ApiResponse.success("Child categories retrieved successfully", categories));
    }
    
    @GetMapping("/level/{level}")
    @Operation(summary = "Get categories by level")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoriesByLevel(@PathVariable Integer level) {
        List<CategoryResponse> categories = categoryService.getCategoriesByLevel(level);
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", categories));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search categories by name")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> searchCategories(
            @RequestParam String searchTerm) {
        List<CategoryResponse> categories = categoryService.searchCategories(searchTerm);
        return ResponseEntity.ok(ApiResponse.success("Categories found", categories));
    }
    
    @GetMapping("/hierarchy")
    @Operation(summary = "Get category hierarchy tree")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategoryHierarchy() {
        List<CategoryResponse> hierarchy = categoryService.getCategoryHierarchy();
        return ResponseEntity.ok(ApiResponse.success("Category hierarchy retrieved successfully", hierarchy));
    }
    
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') or hasAuthority('DELETE_ITEMS')")
    @Operation(summary = "Delete a category")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }
}

