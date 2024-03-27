package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

public class ShowMenuPosition extends ShowMenu {
    private boolean running = true;
    private final PositionManagementService positionManagementService;
    //    Menu menu = new Menu();
    private final Scanner scanner = new Scanner(System.in);

    public ShowMenuPosition() {
        this.positionManagementService = PositionManagementService.getInstance();
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
        while (running) {
            System.out.println("Выберите действие:");
            System.out.println("Меню должностей!");
            System.out.println("1. Создать должность");
            System.out.println("2. Изменить должность");
            System.out.println("3. Удалить должность");
            System.out.println("4. Вывести одну конкретную должность");
            System.out.println("5. Вывести список должностей");
            System.out.println("6. Вывести должности с вложенными сотрудниками");
            System.out.println("7. Назад к основному меню");

            int choice = scanner.nextInt();

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
                    printPositionsEmployees();
                    break;
                case 7:
                    new Menu().doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("***********************************************************");
                    break;
            }
        }
    }

    protected void createPosition() throws JsonProcessingException {
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

    private void printPosition() throws JsonProcessingException {
        System.out.print("Введите ID должности для вывода: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        positionManagementService.printPosition(id);
    }

    private void printAllPositions() throws JsonProcessingException {
        positionManagementService.printAllPositions();
    }

    private void printPositionsEmployees() throws JsonProcessingException {
        positionManagementService.printPositionsEmployees();
    }
}

