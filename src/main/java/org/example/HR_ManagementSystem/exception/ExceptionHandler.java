package org.example.HR_ManagementSystem.exception;

import java.util.InputMismatchException;

public class ExceptionHandler {
    public static void handleException(Exception e) {
        if (e instanceof InputMismatchException) {
            System.out.println("Ошибка ввода: Неверный формат данных!");
        } else if (e instanceof RuntimeException) {
            System.out.println("not found !" + '\n' + "not completed !!!");
        } else {
            System.out.println("Ошибка: " + e.getMessage());
        }
        System.out.println("******************************************************");
    }
}

