package com.substring.quiz.category.category_service.services.impl;

import com.substring.quiz.category.category_service.dto.CategoryDto;
import com.substring.quiz.category.category_service.entities.Category;
import com.substring.quiz.category.category_service.repositories.CategoryRepository;
import com.substring.quiz.category.category_service.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
       Category category= modelMapper.map(categoryDto, Category.class);
        category.setId(UUID.randomUUID().toString());
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto) {
       Category category= categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setActive(categoryDto.isActive());
        Category savedCategory= categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found"));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map(category->modelMapper.map(category,CategoryDto.class)).toList();
    }
}
