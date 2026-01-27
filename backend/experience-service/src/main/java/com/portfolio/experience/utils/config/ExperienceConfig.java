package com.portfolio.experience.utils.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExperienceConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
