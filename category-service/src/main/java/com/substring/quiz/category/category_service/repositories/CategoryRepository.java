package com.substring.quiz.category.category_service.repositories;


import com.substring.quiz.category.category_service.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category,String>{


}
