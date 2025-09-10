package com.substring.quiz.services;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "QuestionsService",url = "http://localhost:9091")
public interface QuestionsService {
}
