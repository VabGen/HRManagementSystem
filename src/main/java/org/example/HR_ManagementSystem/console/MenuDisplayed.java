package org.example.HR_ManagementSystem.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.console.display.EmployeeMenuDisplay;
import org.example.HR_ManagementSystem.console.display.PositionMenuDisplay;
import org.example.HR_ManagementSystem.dao.service.impl.EmployeeServiceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public abstract class MenuDisplayed {

    MenuDisplay menuDisplay;
    PositionMenuDisplay positionMenuDisplay;
    EmployeeMenuDisplay employeeMenuDisplay;

    @Autowired
    public MenuDisplayed(MenuDisplay menuDisplay, PositionMenuDisplay positionMenuDisplay,
                         EmployeeMenuDisplay employeeMenuDisplay) {
        this.menuDisplay = menuDisplay;
        this.positionMenuDisplay = positionMenuDisplay;
        this.employeeMenuDisplay = employeeMenuDisplay;

    }

    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_RESET = "\u001B[0m";
    String horizontalLine = "\u2550".repeat(53);
    Scanner scanner = new Scanner(System.in);

    public MenuDisplayed() {
    }

    public void display() {
        System.out.println("\u2554" + horizontalLine + "\u2557");
        System.out.println("\u2551" + " " + ANSI_GREEN + "Добро пожаловать в систему управления сотрудниками!" + ANSI_RESET + " " + "\u2551");
        System.out.println("\u255A" + horizontalLine + "\u255D");
        System.out.println("*" + "  " + ANSI_BLUE + "\u001B[4m" + "\u001B[1m" + "Выберите действие:" + ANSI_RESET);
    }

    public void noDisplay() throws JsonProcessingException {
        System.out.println(ANSI_RED + "Хотите продолжить работу с программой?" + ANSI_RESET + "(Y/N)");
        String continueChoice = scanner.nextLine().trim().toLowerCase();

        if (continueChoice.equals("n")) {
            Clear.clearConsole();
            System.out.println("\u2554" + horizontalLine + "\u2557");
            System.out.println("\u2551" + " " + ANSI_YELLOW + "Спасибо за использование программы. До свидания!" + "    " + ANSI_RESET + "\u2551");
            System.out.println("\u255A" + horizontalLine + "\u255D");
            MenuDisplay.running = false;
            PositionMenuDisplay.running = false;
            return;
        }
        if (!"yn".contains(continueChoice) || continueChoice.length() > 1) {
            Clear.clearConsole();
            System.out.println(ANSI_RED + "Некорректный выбор. Программа будет завершена." + ANSI_RESET);
            MenuDisplay.running = false;
            EmployeeServiceDaoImpl.running = false;
        } else {
            Clear.clearConsole();
            menuDisplay.doDisplay();
        }
    }

    protected abstract void doDisplay() throws JsonProcessingException;
}
