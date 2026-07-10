package com.ecom.ecomshop.service;

import com.ecom.ecomshop.model.Category;
import com.ecom.ecomshop.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
}
