package com.example.neu.service.impl;

import com.example.neu.dto.category.CategoryResponse;
import com.example.neu.dto.category.CategoryRequest;
import com.example.neu.exception.CategoryNotFoundException;
import com.example.neu.entity.Category;
import com.example.neu.repository.CategoryRepository;
import com.example.neu.service.CategoryService;
import com.example.neu.util.ValueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return ValueMapper.MAPPER.convertToCategoryResponseList(categories);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return ValueMapper.MAPPER.convertToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        // convert request DTO to entity and save
        Category category = ValueMapper.MAPPER.convertToCategory(categoryRequest);
        category = categoryRepository.save(category);
        return ValueMapper.MAPPER.convertToCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategoryById(Long id, CategoryRequest categoryRequest) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        Category fromRequest = ValueMapper.MAPPER.convertToCategory(categoryRequest);
        existing.setName(fromRequest.getName());
        existing.setDescription(fromRequest.getDescription());

        Category saved = categoryRepository.save(existing);
        return ValueMapper.MAPPER.convertToCategoryResponse(saved);
    }

    @Override
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }
}