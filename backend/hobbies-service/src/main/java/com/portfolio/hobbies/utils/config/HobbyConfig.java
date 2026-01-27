package com.portfolio.hobbies.utils.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HobbyConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
