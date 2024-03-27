package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.entity.Position;

import java.util.Scanner;

public class ShowMenuEmployee extends ShowMenu {
    boolean running = true;
    Scanner scanner = new Scanner(System.in);

    private final EmployeeManagementService employeeManagementService;

    public ShowMenuEmployee() {
        this.employeeManagementService = EmployeeManagementService.getInstance();
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
        while (running) {
            System.out.println("1. Создать сотрудника");
            System.out.println("2. Изменить данные сотрудника");
            System.out.println("3. Вывести информацию о сотруднике");
            System.out.println("4. Вывести список сотрудников, отсортированный по фамилии");
            System.out.println("5. Вывести список сотрудников по фильтру");
            System.out.println("6. Уволить сотрудника");
            System.out.println("7. Назад к основному меню");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createEmployee();
                    break;
                case 2:
                    modifyEmployee();
                    break;
                case 3:
                    printEmployee();
                    break;
                case 4:
                    printEmployeesSortedByLastName();
                    break;
                case 5:
                    printEmployeesByFilter();
                    break;
                case 6:
                    terminateEmployee();
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

    private void createEmployee() throws JsonProcessingException {
        scanner.nextLine();
        System.out.println("Введите фамилию сотрудника:");
        String lastName = scanner.nextLine();

        System.out.println("Введите имя сотрудника:");
        String firstName = scanner.nextLine();

        System.out.println("Введите отчество сотрудника:");
        String middleName = scanner.nextLine();

        PositionManagementService.getInstance().printAllPositions();
        System.out.println("Выберите должность сотрудника:");
        int positionId = scanner.nextInt();
        employeeManagementService.createEmployee(lastName, firstName, middleName, positionId);

        System.out.println("Сотрудник успешно создан.");
        System.out.println("***********************************************************");
    }

    private void modifyEmployee() {
        System.out.println("Введите ID сотрудника, данные которого нужно изменить:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите новую фамилию сотрудника:");
        String lastName = scanner.nextLine();

        System.out.println("Введите новое имя сотрудника:");
        String firstName = scanner.nextLine();

        System.out.println("Введите новое отчество сотрудника:");
        String middleName = scanner.nextLine();

        System.out.println("Введите должность сотрудника:");
        String positionName = scanner.nextLine();

        Position position = new Position(id, positionName);
        employeeManagementService.modifyEmployee(id, lastName, firstName, middleName, position);

        System.out.println("Данные сотрудника успешно изменены.");
        System.out.println("***********************************************************");
    }

    private void printEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, информацию о котором нужно вывести:");
        int id = scanner.nextInt();
        scanner.nextLine();
        employeeManagementService.printEmployee(id);
    }

    private void printEmployeesSortedByLastName() throws JsonProcessingException {
        employeeManagementService.printEmployeesSortedByLastName();
    }

    private void printEmployeesByFilter() throws JsonProcessingException {
        scanner.nextLine();
        while (running) {
            System.out.println("Введите фильтр для поиска сотрудников:");
            System.out.println("1: Поиск по Фамилии, Имени, Отчеству");
            System.out.println("2: Поиск по дате создания");
            System.out.println("3: Поиск по должности");
            System.out.println("4: Назад");
            System.out.println("5: Назад к основному меню");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    employeeManagementService.searchByFullName();
                    break;
                case 2:
                    employeeManagementService.searchByCreationDate();
                    break;
                case 3:
                    employeeManagementService.searchByPosition();
                    break;
                case 4:
                    doDisplay();
                    break;
                case 5:
                    new Menu().doDisplay();
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
            }
        }
    }

    private void terminateEmployee() {
        System.out.println("Введите ID сотрудника, которого нужно уволить:");
        int id = scanner.nextInt();
        employeeManagementService.terminateEmployee(id);
        System.out.println("Сотрудник успешно уволен.");
    }
}

