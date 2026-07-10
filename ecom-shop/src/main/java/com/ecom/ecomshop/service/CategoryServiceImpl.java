package com.ecom.ecomshop.service;

import com.ecom.ecomshop.exceptions.APIException;
import com.ecom.ecomshop.exceptions.ResourceNotFoundException;
import com.ecom.ecomshop.model.Category;
import com.ecom.ecomshop.payload.CategoryDTO;
import com.ecom.ecomshop.payload.CategoryResponse;
import com.ecom.ecomshop.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty())
            throw new APIException("No category created till now. ");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTo categoryDTO) {

        Category category = modelMapper.map(CategoryDTO, Category.class);

        Category categoryFromBD = categoryRepository.findByCategoryName(category.getCategoryName());

        if (categoryFromBD != null)
            throw new APIException("Category with the name " + category.getCategoryName() + "already exists !!!");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, Category.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
       Category category = categoryRepository.findById(categoryId)
               .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
       
        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + "deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->  new ResourceNotFoundException("Category", "categoryId", categoryId));

            category.setCategoryId(categoryId);
            savedCategory = categoryRepository.save(category);
            return savedCategory;
    }
}
