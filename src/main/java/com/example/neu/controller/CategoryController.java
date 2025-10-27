package com.example.neu.controller;

import com.example.neu.dto.APIResponse;
import com.example.neu.dto.category.CategoryRequest;
import com.example.neu.dto.category.CategoryResponse;
import com.example.neu.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    public static final String SUCCESS = "SUCCESS";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    ResponseEntity<APIResponse<java.util.List<CategoryResponse>>> getAll() {
        APIResponse<java.util.List<CategoryResponse>> apiResponse = APIResponse.<List<CategoryResponse>>builder()
                .status(SUCCESS)
                .result(categoryService.findAllCategory())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponse<CategoryResponse>> getOne(@PathVariable Long id) {
        APIResponse<CategoryResponse> apiResponse = APIResponse.<CategoryResponse>builder()
                .status(SUCCESS)
                .result(categoryService.getCategoryById(id))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    ResponseEntity<APIResponse<CategoryResponse>> create(@RequestBody CategoryRequest categoryRequest) {
        APIResponse<CategoryResponse> apiResponse = APIResponse.<CategoryResponse>builder()
                .status(SUCCESS)
                .result(categoryService.createCategory(categoryRequest) != null ?
                        // convert created Category entity to response via service call to fetch saved DTO
                        categoryService.getCategoryById(categoryService.createCategory(categoryRequest).getId()) : null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<APIResponse<CategoryResponse>> update(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        APIResponse<CategoryResponse> apiResponse = APIResponse.<CategoryResponse>builder()
                .status(SUCCESS)
                .result(categoryService.updateCategoryById(id, categoryRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(SUCCESS)
                .build();
        return ResponseEntity.noContent().build();
    }
}