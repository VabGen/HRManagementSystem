package org.example.HR_ManagementSystem.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public String getGreeting(String name) {
        return  "I bean" + name;
    }
}
