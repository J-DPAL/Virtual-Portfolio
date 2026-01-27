package com.portfolio.education.utils.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EducationConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
