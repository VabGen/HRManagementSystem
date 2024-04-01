package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.console.EmployeeDataProcessing;

import java.util.Scanner;

public class EmployeeMenuDisplay extends MenuDisplayed {
    public static boolean running = true;
    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper;
    EmployeeDataProcessing employeeDataProcessing;

    public EmployeeMenuDisplay() {
        this.employeeDataProcessing = new EmployeeDataProcessing();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
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
            } catch (Exception ignore) {
                System.out.println("Неверное значение");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    employeeDataProcessing.createEmployees();
                    break;
                case 2:
                    employeeDataProcessing.modifyEmployee();
                    break;
                case 3:
                    employeeDataProcessing.printEmployee();
                    break;
                case 4:
                    employeeDataProcessing.printEmployeesSortedByLastName();
                    break;
                case 5:
                    new EmployeesByFilterDisplay().printEmployeesByFilter();
                    break;
                case 6:
                    employeeDataProcessing.terminateEmployee();
                    break;
                case 7:
                    employeeDataProcessing.terminatedEmployees();
                    break;
                case 8:
                    new MenuDisplay().doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
                    break;
            }
        }
    }
}
