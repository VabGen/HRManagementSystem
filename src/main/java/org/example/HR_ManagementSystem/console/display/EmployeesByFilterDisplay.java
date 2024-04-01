package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.console.EmployeeDataProcessing;

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
        while (running) {
            System.out.println("Введите фильтр для поиска сотрудников:");
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
                    employeeDataProcessing.searchByFullName();
                    break;
                case 2:
                    employeeDataProcessing.searchByCreationDate();
                    break;
                case 3:
                    employeeDataProcessing.searchByPosition();
                    break;
                case 4:
                    new EmployeeMenuDisplay().doDisplay();
                    break;
                case 5:
                    new MenuDisplay().doDisplay();
                    break;
                default:
                    System.out.println("Неверный ввод. Попробуйте еще раз.");
            }
        }
    }
}
