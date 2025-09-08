package com.substring.quiz.services;

import com.substring.quiz.collections.Quiz;
import com.substring.quiz.dto.QuizDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

    QuizDto create(QuizDto quizDto);

    QuizDto update(String quizId, QuizDto quizDto);

    QuizDto findById(String quizId);

    void delete(String quizId);

    List<QuizDto> findAll();

    List<QuizDto> findByCategory(String categoryId);

}
