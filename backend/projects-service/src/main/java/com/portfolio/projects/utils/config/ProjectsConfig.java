package com.portfolio.projects.utils.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectsConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
