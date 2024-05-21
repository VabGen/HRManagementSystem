package org.example.HR_ManagementSystem.configuration;

import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    MenuDisplay menuDisplay;

    public AppConfig(MenuDisplay menuDisplay) {
        this.menuDisplay = menuDisplay;
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            menuDisplay.doDisplay();
        };
    }
}

