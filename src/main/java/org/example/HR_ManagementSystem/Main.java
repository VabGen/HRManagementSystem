package org.example.HR_ManagementSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        SpringApplication.run(Main.class, args);
//  ****************************************************************************************************************
//        Thread springThread = new Thread(() -> SpringApplication.run(Main.class, args));
//        springThread.start();
//
//        MenuDisplay menuDisplay = new MenuDisplay();
//        menuDisplay.doDisplay();
//  ****************************************************************************************************************
    }

    @Bean
    CommandLineRunner runner(){
        return args -> {
            MenuDisplay menuDisplay = new MenuDisplay();
            menuDisplay.doDisplay();
        };
    }
}