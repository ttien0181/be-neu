package com.example.neu.service;

import com.example.neu.dto.category.CategoryRequest;
import com.example.neu.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllCategory();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategoryById(Long id, CategoryRequest categoryRequest);
    void deleteCategoryById(Long id);
}