package org.example.HR_ManagementSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.console.display.MenuDisplay;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

//  ****************************************************************************************************************  \\

        MenuDisplay menuDisplay = new MenuDisplay();
        menuDisplay.doDisplay();

//  ****************************************************************************************************************  \\

    }
}