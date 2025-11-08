package com.substring.quiz.services;

import com.substring.quiz.collections.Quiz;
import com.substring.quiz.dto.CategoryDto;
import com.substring.quiz.dto.QuizDto;
import com.substring.quiz.repository.QuizRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class QuizServiceImpl implements QuizService{

    private Logger logger=org.slf4j.LoggerFactory.getLogger(QuizServiceImpl.class);

    private final QuizRepository quizRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    private final CategoryService categoryService;

    private final CategoryFeignService categoryFeignService;

    private StreamBridge streamBridge;
    public QuizServiceImpl(QuizRepository quizRepository, ModelMapper modelMapper, RestTemplate restTemplate, CategoryService categoryService, CategoryFeignService categoryFeignService, StreamBridge streamBridge) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.categoryService = categoryService;
        this.categoryFeignService = categoryFeignService;
        this.streamBridge=streamBridge;
    }

    @Override
    public QuizDto create(QuizDto quizDto) {
        Quiz quiz=  modelMapper.map(quizDto,Quiz.class);
        quiz.setId(UUID.randomUUID().toString());
        String url="lb://CATEGORY-SERVICE/api/v1/categories/"+quizDto.getCategoryId();
        logger.info(url);
//        Call to category Service
        CategoryDto category= restTemplate.getForObject(url, CategoryDto.class);
        logger.info("Category exists:" + category.getTitle());
        Quiz savedQuiz= quizRepository.save(quiz);

        QuizDto quizDto1=modelMapper.map(savedQuiz,QuizDto.class);
        quizDto1.setCategoryDto(category);

//        Quiz created event ko publish kar diya
        publishQuizCreatedEvent(quizDto1);

        return quizDto1;
    }

//    event Publish
    private void publishQuizCreatedEvent(QuizDto quizDto) {
         logger.info("Quiz Created, going to Published quizCreatedEvent");
         var status = this.streamBridge.send("quizCreatedBinding-out-0",quizDto);
         if(status)
         {
             logger.info("Event is sent to broker");
         }
         else {
             logger.info("Failed to send event to broker");
         }
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
        String url="lb://CATEGORY-SERVICE/api/v1/categories/"+categoryId;
        logger.info(url);
//        Call to category Service
        CategoryDto category= restTemplate.getForObject(url, CategoryDto.class);
        logger.info("Category Exist: {}", category.getTitle());
        logger.info("Call Completed");

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

//        getting category of all quiz
            List<QuizDto> quizDtos=  quizzes.stream().map(quiz -> {

            String categoryId=quiz.getCategoryId();
            QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);

//            call to quiz service using webclient
               CategoryDto categoryDto= this.categoryService.findById(categoryId);
               quizDto.setCategoryDto(categoryDto);
            return quizDto;
        }).toList();
        return quizDtos;
    }

    @Override
    public List<QuizDto> findByCategory(String categoryId) {
       List<Quiz> quizzes=quizRepository.findByCategoryId(categoryId);
        return quizzes.stream().map(quiz->{
            QuizDto quizDto=modelMapper.map(quiz,QuizDto.class);
            CategoryDto categoryDto= null;

            try{
                categoryDto= categoryFeignService.findById(quizDto.getCategoryId());
            }
            catch (FeignException.NotFound ex)
            {
                logger.error("Category not found");
            }

            quizDto.setCategoryDto(categoryDto);

            return quizDto;
        }).toList();

    }
}
