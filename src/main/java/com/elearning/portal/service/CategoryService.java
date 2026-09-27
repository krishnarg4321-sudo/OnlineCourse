package com.elearning.portal.service;

import com.elearning.portal.dto.request.CategoryRequest;
import com.elearning.portal.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
}
