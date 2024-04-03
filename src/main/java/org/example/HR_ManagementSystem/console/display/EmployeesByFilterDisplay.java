package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.collection.data.EmployeeDataProcessing;
import org.example.HR_ManagementSystem.console.Clear;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import static org.example.HR_ManagementSystem.console.display.EmployeeMenuDisplay.running;

public class EmployeesByFilterDisplay {
    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper;
    EmployeeDataProcessing employeeDataProcessing;

    public EmployeesByFilterDisplay() {
        this.employeeDataProcessing = new EmployeeDataProcessing();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public void printEmployeesByFilter() throws JsonProcessingException {
        Clear.clearConsole();
        while (running) {
            System.out.println("*" + "  " + "\u001B[34m" + "\u001B[4m" + "\u001B[1m" + "Введите фильтр для поиска сотрудников:" + "\u001B[0m");
            System.out.println("1: Поиск по Фамилии, Имени, Отчеству");
            System.out.println("2: Поиск по дате создания");
            System.out.println("3: Поиск по должности");
            System.out.println("4: Назад");
            System.out.println("5: Назад к основному меню");

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
                    Clear.clearConsole();
                    searchByFullName();
                    break;
                case 2:
                    Clear.clearConsole();
                    searchByCreationDate();
                    break;
                case 3:
                    Clear.clearConsole();
                    searchByPosition();
                    break;
                case 4:
                    Clear.clearConsole();
                    new EmployeeMenuDisplay().doDisplay();
                    break;
                case 5:
                    Clear.clearConsole();
                    new MenuDisplay().doDisplay();
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
            }
        }
    }

    private void searchByFullName() throws JsonProcessingException {
        System.out.println("Введите данные для поиска сотрудников:");
        scanner.nextLine();
        String fullName = scanner.nextLine();
        try {
            List<EmployeeDTO> employeeMapOptional = employeeDataProcessing.searchByFullName(fullName);
            System.out.println(objectMapper.writeValueAsString(employeeMapOptional));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void searchByCreationDate() throws JsonProcessingException {
//          2024-04-03T14:30:33Z
        try {
            System.out.println("Формат даты: \"yyyy-MM-ddTHH:mm:ssZ\"");
            System.out.println("Дата с:");
            Instant fromDate = Instant.parse(scanner.next());

            System.out.println("Дата по:");
            Instant toDate = Instant.parse(scanner.next());
            List<EmployeeDTO> employeeList = employeeDataProcessing.searchByCreationDate(fromDate, toDate);
            System.out.println(objectMapper.writeValueAsString(employeeList));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void searchByPosition() throws JsonProcessingException {
        System.out.println("Введите ID должности для поиска:");
        int positionId = scanner.nextInt();
        try {
            List<EmployeeDTO> dtos = employeeDataProcessing.searchByPosition(positionId);
            System.out.println(objectMapper.writeValueAsString(dtos));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
