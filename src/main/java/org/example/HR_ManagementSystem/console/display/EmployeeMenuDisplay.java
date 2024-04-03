package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.collection.data.EmployeeDataProcessing;
import org.example.HR_ManagementSystem.collection.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.collection.service.PositionManagementService;
import org.example.HR_ManagementSystem.console.Clear;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.example.HR_ManagementSystem.console.MenuDisplayed;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;

import java.util.List;
import java.util.Scanner;

public class EmployeeMenuDisplay extends MenuDisplayed {
    public static boolean running = true;
    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper;
    EmployeeDataProcessing employeeDataProcessing;
    EmployeeManagementService employeeManagementService;

    public EmployeeMenuDisplay() {
        this.employeeDataProcessing = new EmployeeDataProcessing();
        this.employeeManagementService = new EmployeeManagementService();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
        Clear.clearConsole();
        while (running) {
            System.out.println(ANSI_YELLOW + "Меню сотрудников!" + ANSI_RESET);
            System.out.println("Выберите действие:");
            System.out.println("1. Создать сотрудника");
            System.out.println("2. Изменить данные сотрудника");
            System.out.println("3. Вывести информацию о сотруднике");
            System.out.println("4. Вывести список сотрудников, отсортированный по фамилии");
            System.out.println("5. Вывести список сотрудников по фильтру");
            System.out.println("6. Уволить сотрудника");
            System.out.println("7. Уволенные сотрудники");
            System.out.println("8. Назад к основному меню");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception ignore) {
                System.out.println("Неверное значение");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    Clear.clearConsole();
                    createEmployees();
                    break;
                case 2:
                    Clear.clearConsole();
                    modifyEmployee();
                    break;
                case 3:
                    Clear.clearConsole();
                    printEmployee();
                    break;
                case 4:
                    Clear.clearConsole();
                    printEmployeesSortedByLastName();
                    break;
                case 5:
                    Clear.clearConsole();
                    new EmployeesByFilterDisplay().printEmployeesByFilter();
                    break;
                case 6:
                    Clear.clearConsole();
                    terminateEmployee();
                    break;
                case 7:
                    Clear.clearConsole();
                    terminatedEmployees();
                    break;
                case 8:
                    Clear.clearConsole();
                    new MenuDisplay().doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
                    break;
            }
        }
    }

    private void createEmployees() throws JsonProcessingException {
        System.out.println("Введите фамилию сотрудника:");
        String lastName = scanner.nextLine();

        System.out.println("Введите имя сотрудника:");
        String firstName = scanner.nextLine();

        System.out.println("Введите отчество сотрудника:");
        String middleName = scanner.nextLine();

        PositionManagementService.getInstance().printAllPositions();
        System.out.println("Выберите ID должности сотрудника:");
        int positionId = scanner.nextInt();
        try {
            EmployeeDTO employeeDTO = employeeDataProcessing.createEmployee(lastName, firstName, middleName, positionId);
            System.out.println("Сотрудник успешно создан: " + objectMapper.writeValueAsString(employeeDTO));
            System.out.println("******************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void modifyEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, данные которого нужно изменить:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            EmployeeDTO employee = employeeDataProcessing.modifyEmployee(id);
            System.out.println("Введите новую фамилию сотрудника:");
            employee.setLastName(scanner.nextLine());

            System.out.println("Введите новое имя сотрудника:");
            employee.setFirstName(scanner.nextLine());

            System.out.println("Введите новое отчество сотрудника:");
            employee.setMiddleName(scanner.nextLine());

            System.out.println("Введите ID должности для сотрудника:");
            employee.setPositionId(scanner.nextInt());

            System.out.println("Данные сотрудника успешно изменены.");
            System.out.println(objectMapper.writeValueAsString(employee));
            System.out.println("***********************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void printEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, информацию о котором нужно вывести:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            EmployeeDTO printEmployee = employeeDataProcessing.printEmployee(id);
            System.out.println(objectMapper.writeValueAsString(printEmployee));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void printEmployeesSortedByLastName() throws JsonProcessingException {
        try {
            List<EmployeeDTO> employeeList = employeeDataProcessing.printEmployeesSortedByLastName();
            System.out.println(objectMapper.writeValueAsString(employeeList));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void terminateEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, которого нужно уволить:");
        int id = scanner.nextInt();
        try {
            EmployeeDTO terminateEmployee = employeeDataProcessing.terminateEmployee(id);
            System.out.println(objectMapper.writeValueAsString(terminateEmployee) + "Сотрудник успешно уволен.");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void terminatedEmployees() throws JsonProcessingException {
        try {
            List<EmployeeDTO> dtos = employeeDataProcessing.terminatedEmployees();
            System.out.println(objectMapper.writeValueAsString(dtos));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
