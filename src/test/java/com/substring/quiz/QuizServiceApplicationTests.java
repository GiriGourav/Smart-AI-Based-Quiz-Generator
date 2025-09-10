package com.substring.quiz;

import com.substring.quiz.dto.CategoryDto;
import com.substring.quiz.services.CategoryFeignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QuizServiceApplicationTests {

    @Autowired
    private CategoryFeignService categoryFeignService;


	@Test
	public void testFeignCategories() {
        System.out.println("Getting All Categories");
        List<CategoryDto> all=categoryFeignService.findAll();
        all.forEach(categoryDto->System.out.println(categoryDto.getTitle()));

//        Assertions.assertEquals(4,all.size());
        Assertions.assertNotNull(all);
	}

    @Test
    public void testFeignSingleCategory(){
        System.out.println("Getting single category");
        CategoryDto categoryDto=categoryFeignService.findById("1c45f0b0-c1a4-45b7-83c0-f915a6a1400c");
        System.out.println(categoryDto.getTitle());
        Assertions.assertNotNull(categoryDto);
    }


}
