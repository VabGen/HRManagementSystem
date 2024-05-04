package org.example.HR_ManagementSystem.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.console.display.EmployeeMenuDisplay;
import org.example.HR_ManagementSystem.console.display.PositionMenuDisplay;

import java.util.Scanner;

public class MenuDisplay extends MenuDisplayed {
    static boolean running = true;

    @Override
    public void doDisplay() throws JsonProcessingException {
        display();
        Scanner scanner = new Scanner(System.in);
        while (running) {
            EmployeeMenuDisplay.running = true;
            PositionMenuDisplay.running = true;
            System.out.println("1: СОТРУДНИКИ");
            System.out.println("2: ДОЛЖНОСТИ");
            System.out.println("3:" + " " + ANSI_RED + "\u001B[1m" + "\u001B[4m" + "Выход" + ANSI_RESET);

            int input;
            try {
                input = scanner.nextInt();
            } catch (Exception ignore) {
                System.out.println(ANSI_RED + "\u001B[1m" + "Неверное значение" + ANSI_RESET);
                scanner.nextLine();
                continue;
            }
            switch (input) {
                case 1:
                    Clear.clearConsole();
                    EmployeeMenuDisplay employeeMenuDisplay = new EmployeeMenuDisplay();
                    employeeMenuDisplay.doDisplay();
                    break;
                case 2:
                    Clear.clearConsole();
                    new PositionMenuDisplay().doDisplay();
                    break;
                case 3:
                    Clear.clearConsole();
                    noDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
            }
        }
    }
}