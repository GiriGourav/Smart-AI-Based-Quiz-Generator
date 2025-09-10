package com.substring.quiz.controllers;

import com.substring.quiz.dto.QuizDto;
import com.substring.quiz.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDto> create(@RequestBody QuizDto quizDto)
    {
        return new  ResponseEntity<> (quizService.create(quizDto), HttpStatus.CREATED);
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<QuizDto> update(@PathVariable String quizId,
                                          @RequestBody QuizDto quizDto)
    {
          return new ResponseEntity<>(quizService.update(quizId,quizDto),HttpStatus.OK);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDto> getById(@PathVariable String quizId)
    {
        return new ResponseEntity<>(quizService.findById(quizId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> findAll()
    {
        return new ResponseEntity<>(quizService.findAll(),HttpStatus.OK);
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> delete(@PathVariable String quizId)
    {
        quizService.delete(quizId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<QuizDto>> getByCategoryId(@PathVariable String categoryId)
    {
        return new ResponseEntity<>(quizService.findByCategory(categoryId),HttpStatus.OK);
    }
}
