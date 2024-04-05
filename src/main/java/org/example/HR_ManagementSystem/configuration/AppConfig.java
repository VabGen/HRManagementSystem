package org.example.HR_ManagementSystem.configuration;

import org.example.HR_ManagementSystem.collection.data.EmployeeDataProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public EmployeeDataProcessing employeeDataProcessing() {
        return new EmployeeDataProcessing();
    }

//    @Bean
//    public EmployeeFilterController employeeFilterController(EmployeeDataProcessing employeeDataProcessing) {
//        return new EmployeeFilterController(employeeDataProcessing);
//    }
}

