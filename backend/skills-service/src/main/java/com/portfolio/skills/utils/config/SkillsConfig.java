package com.portfolio.skills.utils.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkillsConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
