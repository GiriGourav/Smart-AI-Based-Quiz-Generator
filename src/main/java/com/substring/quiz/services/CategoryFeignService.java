package com.substring.quiz.services;

import com.substring.quiz.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "category-service",url = "http://localhost:9093/api/v1")
public interface CategoryFeignService {
    @GetMapping("/categories")
    List<CategoryDto> findAll();

    @GetMapping("/categories/{categoryId}")
    CategoryDto findById(@PathVariable String categoryId);

    @PostMapping("/categories")
    CategoryDto create(@RequestBody CategoryDto categoryDto);

    @PutMapping("/categories/{categoryId}")
    CategoryDto update(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto);

    @DeleteMapping("/categories/{categoryId}")
    void delete(@PathVariable String categoryId);
}
