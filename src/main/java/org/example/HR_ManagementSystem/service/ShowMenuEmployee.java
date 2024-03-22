package org.example.HR_ManagementSystem.service;

import org.example.HR_ManagementSystem.entity.Position;

import java.util.Arrays;
import java.util.Scanner;

public class ShowMenuEmployee extends ShowMenu {
    boolean running = true;
    int choice = 0;

    EmployeeManagementService system = new EmployeeManagementService();
    Scanner scanner = new Scanner(System.in);

    @Override
    public void doDisplay() {
        display();

        while (running) {
            System.out.println("1. Создать сотрудника");
            System.out.println("2. Изменить данные сотрудника");
            System.out.println("3. Вывести информацию о сотруднике");
            System.out.println("4. Вывести список сотрудников, отсортированный по фамилии");
            System.out.println("5. Вывести список сотрудников по фильтру");
            System.out.println("6. Уволить сотрудника");
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

            switch (Character.toLowerCase(choice)) {
                case 1:
                    createEmployee(system, scanner);
                    break;
                case 2:
                    modifyEmployee(system, scanner);
                    break;
                case 3:
                    printEmployee(system, scanner);
                    break;
                case 4:
                    system.printEmployeesSortedByLastName();
                    break;
                case 5:
                    printEmployeesByFilter(system, scanner);
                    break;
                case 6:
                    terminateEmployee(system, scanner);
                    break;
                case 7:
                    running = false;
                    String horizontalLine = "\u2550".repeat(53);
                    System.out.println("\u2554" + horizontalLine + "\u2557");
                    System.out.println("\u2551" + " " + "Спасибо за использование программы. До свидания!" + "    " + "\u2551");
                    System.out.println("\u255A" + horizontalLine + "\u255D");
                    break;

                default:
                    System.out.println("Некорректный выбор. Попробуйте еще раз.");
                    continue;
            }
        }
        scanner.close();
    }

    private static void createEmployee(EmployeeManagementService system, Scanner scanner) {
        System.out.println("Введите фамилию сотрудника:");
        String lastName = scanner.nextLine();

        System.out.println("Введите имя сотрудника:");
        String firstName = scanner.nextLine();

        System.out.println("Введите отчество сотрудника:");
        String middleName = scanner.nextLine();

        System.out.println("Введите должность сотрудника:");
        String positionName = scanner.nextLine();


        int id = 1;
        Position position = new Position(id, positionName);
        system.createEmployee(lastName, firstName, middleName, position);

        System.out.println("Сотрудник успешно создан.");
        System.out.println("***********************************************************");
    }

    private static void modifyEmployee(EmployeeManagementService system, Scanner scanner) {
        System.out.println("Введите ID сотрудника, данные которого нужно изменить:");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите новую фамилию сотрудника:");
        String lastName = scanner.nextLine();

        System.out.println("Введите новое имя сотрудника:");
        String firstName = scanner.nextLine();

        System.out.println("Введите новое отчество сотрудника:");
        String middleName = scanner.nextLine();

        System.out.println("Введите новую должность сотрудника:");
        String positionName = scanner.nextLine();

        Position position = new Position(id, positionName);
        system.modifyEmployee(id, lastName, firstName, middleName, position);

        System.out.println("Данные сотрудника успешно изменены.");
        System.out.println("***********************************************************");
    }

    private static void printEmployee(EmployeeManagementService system, Scanner scanner) {
        System.out.println("Введите ID сотрудника, информацию о котором нужно вывести:");
        int id = scanner.nextInt();
        scanner.nextLine();
        system.printEmployee(id);
    }

    private static void printEmployeesByFilter(EmployeeManagementService system, Scanner scanner) {
        System.out.println("Введите фильтр для поиска сотрудников:");
        String filter = scanner.nextLine();
        system.printEmployeesByFilter(filter);
    }

    private static void terminateEmployee(EmployeeManagementService system, Scanner scanner) {
        System.out.println("Введите ID сотрудника, которого нужно уволить:");
        int id = scanner.nextInt();
        scanner.nextLine();
        system.terminateEmployee(id);
        System.out.println("Сотрудник успешно уволен.");
    }
}

