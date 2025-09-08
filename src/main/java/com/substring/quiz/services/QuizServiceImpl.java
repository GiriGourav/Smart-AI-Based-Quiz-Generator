package com.substring.quiz.services;

import com.substring.quiz.collections.Quiz;
import com.substring.quiz.dto.CategoryDto;
import com.substring.quiz.dto.QuizDto;
import com.substring.quiz.repository.QuizRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
@Service
public class QuizServiceImpl implements QuizService{

    private Logger logger=org.slf4j.LoggerFactory.getLogger(QuizServiceImpl.class);

    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    public QuizServiceImpl(QuizRepository quizRepository, ModelMapper modelMapper, RestTemplate restTemplate) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public QuizDto create(QuizDto quizDto) {
        Quiz quiz=  modelMapper.map(quizDto,Quiz.class);
        quiz.setId(UUID.randomUUID().toString());
        String url="http://localhost:9093/api/v1/categories/"+quizDto.getCategoryId();
        logger.info(url);
//        Call to category Service
        CategoryDto category= restTemplate.getForObject(url, CategoryDto.class);
        logger.info("Category exists:" + category.getTitle());
        Quiz savedQuiz= quizRepository.save(quiz);

        QuizDto quizDto1=modelMapper.map(savedQuiz,QuizDto.class);
        return quizDto1;
    }

    @Override
    public QuizDto update(String quizId, QuizDto quizDto) {
        Quiz  quiz=quizRepository.findById(quizId).orElseThrow(()-> new RuntimeException("Quiz not found"));
        quiz.setDescription(quizDto.getDescription());
        quiz.setTitle(quizDto.getTitle());
        quiz.setImageUrl(quizDto.getImageUrl());
        quiz.setCreatedBy(quizDto.getCreatedBy());
        quiz.setMaxMarks(quizDto.getMaxMarks());
        quiz.setLive(quizDto.getLive());
        quiz.setNoOfQuestions(quizDto.getNoOfQuestions());
        quiz.setPassingMarks(quizDto.getPassingMarks());
        quiz.setTimeLimit(quizDto.getTimeLimit());
        quiz.setCategoryId(quizDto.getCategoryId());


        Quiz updatedQuiz=quizRepository.save(quiz);

        return modelMapper.map(updatedQuiz,QuizDto.class);
    }

    @Override
    public QuizDto findById(String quizId) {
        Quiz  quiz=quizRepository.findById(quizId).orElseThrow(()-> new RuntimeException("Quiz not found"));

       QuizDto quizDto= modelMapper.map(quiz,QuizDto.class);
        String categoryId=quiz.getCategoryId();
        String url="http://localhost:9093/api/v1/categories/"+categoryId;
        logger.info(url);
//        Call to category Service
        CategoryDto category= restTemplate.getForObject(url, CategoryDto.class);
        quizDto.setCategoryDto(category);


        return quizDto;
    }

    @Override
    public void delete(String quizId) {
        Quiz  quiz=quizRepository.findById(quizId).orElseThrow(()-> new RuntimeException("Quiz not found"));
        quizRepository.delete(quiz);
    }

    @Override
    public List<QuizDto> findAll() {
        List<Quiz> quizzes=quizRepository.findAll();
        return quizzes.stream().map(quiz->modelMapper.map(quiz,QuizDto.class)).toList();
    }

    @Override
    public List<QuizDto> findByCategory(String categoryId) {
       List<Quiz> quizzes=quizRepository.findByCategoryId(categoryId);
        return quizzes.stream().map(quiz->modelMapper.map(quiz,QuizDto.class)).toList();
    }
}
