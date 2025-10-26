package com.substring.quiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
@RefreshScope
public class ConfigTestController {

    @Value("${config.value}")
    String configValue;

    @RequestMapping
    public String test()
    {
        return configValue;
    }
}
