package org.example.HR_ManagementSystem.configuration;

import org.example.HR_ManagementSystem.service.DemoService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DemoService demoService() {
        return new DemoService();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}

