package org.example.HR_ManagementSystem.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.console.display.EmployeeMenuDisplay;
import org.example.HR_ManagementSystem.console.display.PositionMenuDisplay;
import org.example.HR_ManagementSystem.dao.service.EmployeeServiceDao;
import org.example.HR_ManagementSystem.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuDisplay extends MenuDisplayed {

    static boolean running = true;
    PositionService positionService;
    PositionMenuDisplay positionMenuDisplay;
    EmployeeServiceDao employeeServiceDao;
    EmployeeMenuDisplay employeeMenuDisplay;

    @Autowired
    public MenuDisplay(PositionService positionService, PositionMenuDisplay positionMenuDisplay,
                       EmployeeServiceDao employeeServiceDao, EmployeeMenuDisplay employeeMenuDisplay) {
        this.positionService = positionService;
        this.positionMenuDisplay = positionMenuDisplay;
        this.employeeServiceDao = employeeServiceDao;
        this.employeeMenuDisplay = employeeMenuDisplay;
    }

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
                    employeeMenuDisplay.doDisplay();
                    break;
                case 2:
                    Clear.clearConsole();
                    positionMenuDisplay.doDisplay();
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
