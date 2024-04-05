package org.example.HR_ManagementSystem.exception;

import org.example.HR_ManagementSystem.console.Clear;

import java.util.InputMismatchException;

public class ExceptionHandler {
    public static void handleException(Exception e) {
        Clear.clearConsole();
        if (e instanceof InputMismatchException) {
            System.out.println("\u001B[31m" + "Input error: Invalid data format!" + "\u001B[0m");
        } else if (e instanceof RuntimeException) {
            System.out.println("not found !" + '\n' + "not completed !!!");
        } else {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("******************************************************");
    }
}

