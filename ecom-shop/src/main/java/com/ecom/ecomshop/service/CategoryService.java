package com.ecom.ecomshop.service;

import com.ecom.ecomshop.model.Category;
import com.ecom.ecomshop.payload.CategoryDTO;
import com.ecom.ecomshop.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
