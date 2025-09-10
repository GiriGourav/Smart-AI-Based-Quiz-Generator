package com.substring.quiz.services;

import com.substring.quiz.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryDto findById(String categoryId);

    List<CategoryDto> findAll();

    CategoryDto create(CategoryDto categoryDto);


    CategoryDto update(String categoryId, CategoryDto categoryDto);

    void delete(String categoryId);
}
