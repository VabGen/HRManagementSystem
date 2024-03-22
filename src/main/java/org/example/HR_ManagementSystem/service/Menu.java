package org.example.HR_ManagementSystem.service;

import java.util.Scanner;

public class Menu extends ShowMenu {
    @Override
    public void doDisplay() {
        display();
        Scanner scanner = new Scanner(System.in);

        System.out.println("1: Сотрудники");
        System.out.println("2: Должности");

        boolean running = true;
        while (running) {
            int input = Integer.parseInt(scanner.nextLine().trim());
            switch (input) {
                case 1:
                    ShowMenuEmployee showMenuEmployee = new ShowMenuEmployee();
                    showMenuEmployee.doDisplay();
                    break;
                case 2:
                    ShowMenuPosition showMenuPosition = new ShowMenuPosition();
                    showMenuPosition.doDisplay();
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
                    break;
            }
            scanner.close();
        }
    }
}
