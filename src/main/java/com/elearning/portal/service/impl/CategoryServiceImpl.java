package com.elearning.portal.service.impl;

import com.elearning.portal.dto.request.CategoryRequest;
import com.elearning.portal.dto.response.CategoryResponse;
import com.elearning.portal.entity.Category;
import com.elearning.portal.exception.NotFoundException;
import com.elearning.portal.repository.CategoryRepository;
import com.elearning.portal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category c = new Category();
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        return map(categoryRepository.save(c));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        c.setName(request.getName());
        c.setDescription(request.getDescription());
        return map(categoryRepository.save(c));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryResponse map(Category category) {
        return CategoryResponse.builder().id(category.getId()).name(category.getName()).description(category.getDescription()).build();
    }
}
