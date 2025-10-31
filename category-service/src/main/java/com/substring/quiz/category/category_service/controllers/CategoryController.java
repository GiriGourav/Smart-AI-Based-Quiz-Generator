package com.substring.quiz.category.category_service.controllers;

import com.substring.quiz.category.category_service.dto.CategoryDto;
import com.substring.quiz.category.category_service.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @RequestBody CategoryDto categoryDto)
    {
       CategoryDto category= this.categoryService.createCategory(categoryDto);
       return new ResponseEntity<> (category, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String id,
            @RequestBody CategoryDto categoryDto)
    {
        CategoryDto updatedCategory= this.categoryService.updateCategory(id,categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable String id
    ){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable String id
    ){
        CategoryDto category=this.categoryService.getCategory(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        List<CategoryDto> allCategories=this.categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

}
