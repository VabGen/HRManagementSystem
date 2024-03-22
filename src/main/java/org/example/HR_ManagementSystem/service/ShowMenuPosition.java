package org.example.HR_ManagementSystem.service;

import java.util.Arrays;
import java.util.Scanner;

public class ShowMenuPosition extends ShowMenu {
    private boolean running = true;
    private int choice = 0;

    PositionManagementService positionManagementService = new PositionManagementService();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void doDisplay() {
        display();

        while (running) {
            System.out.println("Выберите действие:");
            System.out.println("Меню должностей!");
            System.out.println("1. Создать должность");
            System.out.println("2. Изменить должность");
            System.out.println("3. Удалить должность");
            System.out.println("4. Вывести одну конкретную должность");
            System.out.println("5. Вывести список должностей");
            System.out.println("6. Вывести сотрудников по фильтру");
            System.out.println("7. Выйти из программы");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                int[] validChoices = {1, 2, 3, 4, 5, 6, 7};
                if (Arrays.stream(validChoices).noneMatch(x -> x == choice)) {
                    System.out.println(ANSI_RED + "== Хотите продолжить работу с программой? (y/n)" + ANSI_RESET);
                    String continueChoice = scanner.nextLine().trim().toLowerCase();
                    if (!continueChoice.equals("y")) {
                        running = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage() + "\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                System.out.println("***********************************************************");
            }

            switch (choice) {
                case 1:
                    createPosition();
                    break;
                case 2:
                    modifyPosition();
                    break;
                case 3:
                    deletePosition();
                    break;
                case 4:
                    printPosition();
                    break;
                case 5:
                    printAllPositions();
                    break;
                case 6:
                    printEmployeesByFilter();
                    break;
                case 7:
                    exit();
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, выберите действие из списка.");
                    continue;
            }
        }
        scanner.close();
    }

    private void createPosition() {
        System.out.print("Введите имя должности: ");
        String name = scanner.nextLine();
        positionManagementService.createPosition(name);
    }

    private void modifyPosition() {
        System.out.print("Введите ID должности для изменения: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите новое имя должности: ");
        String newName = scanner.nextLine();
        positionManagementService.modifyPosition(id, newName);
    }

    private void deletePosition() {
        System.out.print("Введите ID должности для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        positionManagementService.deletePosition(id);
    }

    private void printPosition() {
        System.out.print("Введите ID должности для вывода: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        positionManagementService.printPosition(id);
    }

    private void printAllPositions() {
        positionManagementService.printAllPositions();
    }

    private void printEmployeesByFilter() {
        System.out.print("Введите фильтр для вывода сотрудников: ");
        String filter = scanner.nextLine();
        positionManagementService.printEmployeesByFilter(filter);
    }

    private void exit() {
        running = false;
        System.out.println("Программа завершена.");
    }
}

