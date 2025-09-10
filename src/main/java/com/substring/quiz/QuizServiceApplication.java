package com.substring.quiz;

import com.substring.quiz.collections.Quiz;
import com.substring.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class QuizServiceApplication implements CommandLineRunner {

    @Autowired
    private QuizRepository quizRepository;
	public static void main(String[] args){
        SpringApplication.run(QuizServiceApplication.class, args);}

    @Override
    public void run(String... args) throws Exception
    {

//       Quiz quiz= Quiz.builder()
//                .id(UUID.randomUUID().toString())
//                .title("Python basic quiz")
//                .description("This is python basic quiz")
//                .maxMarks(100)
//                .timeLimit(30)
//                .createdBy("Gourav")
//                .noOfQuestions(10)
//                .imageUrl("https://www.google.com/imgres?q=image%20quiz&imgurl=https%3A%2F%2Ft3.ftcdn.net%2Fjpg%2F02%2F90%2F39%2F00%2F360_F_290390054_92MXhhVdHu46JuZnl3xK9e7w2jlv33O3.jpg&imgrefurl=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dquiz&docid=JUPhqK_blTn_CM&tbnid=z5hReYThJ1U8VM&vet=12ahUKEwiHk5mH9b-PAxVne_UHHfasGA8QM3oECBUQAA..i&w=484&h=360&hcb=2&ved=2ahUKEwiHk5mH9b-PAxVne_UHHfasGA8QM3oECBUQAA")
//                .live(true)
//                .passingMarks(30)
//                .build();
//
//        Quiz saved=quizRepository.save(quiz);
//        System.out.printf("Quiz saved with id: "+savedaved.getId());


    }

}
