package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class Menu extends ShowMenu {
    static boolean running = true;

    @Override
    public void doDisplay() throws JsonProcessingException {
        display();
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("1: Сотрудники");
            System.out.println("2: Должности");
            System.out.println("3:" + " " + ANSI_RED + "\u001B[1m" + "Выход" + ANSI_RESET);
            int input;

            try {
                input = scanner.nextInt();
            } catch (Exception ignore) {
                System.out.println("Неверное значение");
                scanner.nextLine();
                continue;
            }

            switch (input) {
                case 1:
                    ShowMenuEmployee showMenuEmployee = new ShowMenuEmployee();
                    showMenuEmployee.doDisplay();
                    break;
                case 2:
                    ShowMenuPosition showMenuPosition = new ShowMenuPosition();
                    showMenuPosition.doDisplay();
                    break;
                case 3:
                    noDisplay();
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
            }
        }
    }
}
