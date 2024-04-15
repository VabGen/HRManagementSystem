package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.console.EmployeeDataProcessing;

import java.util.Scanner;

public abstract class MenuDisplayed {
    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_RESET = "\u001B[0m";
    String horizontalLine = "\u2550".repeat(53);
    Scanner scanner = new Scanner(System.in);

    public void display() {
        System.out.println("\u2554" + horizontalLine + "\u2557");
        System.out.println("\u2551" + " " + ANSI_GREEN + "Добро пожаловать в систему управления сотрудниками!" + ANSI_RESET + " " + "\u2551");
        System.out.println("\u255A" + horizontalLine + "\u255D");
        System.out.println("*" + "  " + ANSI_BLUE + "\u001B[1m" + "Выберите действие: \n" + ANSI_RESET);
    }

    public void noDisplay() throws JsonProcessingException {
        System.out.println(ANSI_RED + "Хотите продолжить работу с программой?" + ANSI_RESET + "(Y/N)");
        String continueChoice = scanner.nextLine().trim().toLowerCase();

        if (continueChoice.equals("n")) {
            System.out.println("\u2554" + horizontalLine + "\u2557");
            System.out.println("\u2551" + " " + ANSI_YELLOW + "Спасибо за использование программы. До свидания!" + "    " + ANSI_RESET + "\u2551");
            System.out.println("\u255A" + horizontalLine + "\u255D");
            MenuDisplay.running = false;
            new EmployeeMenuDisplay().running = false;
            new PositionMenuDisplay().running = false;
            return;
        }
        if (!"yn".contains(continueChoice) || continueChoice.length() > 1) {
            System.out.println(ANSI_RED + "Некорректный выбор. Программа будет завершена." + ANSI_RESET);
            MenuDisplay.running = false;
            EmployeeDataProcessing.running = false;
        } else {
            new MenuDisplay().doDisplay();
        }
    }

    protected abstract void doDisplay() throws JsonProcessingException;
}