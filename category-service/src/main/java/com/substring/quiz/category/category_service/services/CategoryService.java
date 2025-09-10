package com.substring.quiz.category.category_service.services;

import com.substring.quiz.category.category_service.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(String categoryId, CategoryDto categoryDto);

    CategoryDto getCategory(String categoryId);

    void deleteCategory(String categoryId);

    List<CategoryDto> getAllCategories();


}
