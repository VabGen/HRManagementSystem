package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.console.Clear;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.example.HR_ManagementSystem.console.MenuDisplayed;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.service.EmployeeService;
import org.example.HR_ManagementSystem.source.data.EmployeeServiceDao;
import org.example.HR_ManagementSystem.source.data.PositionServiceDao;
import org.example.HR_ManagementSystem.source.data.impl.EmployeeServiceDaoImpl;
import org.example.HR_ManagementSystem.source.model.Employee;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class EmployeeMenuDisplay extends MenuDisplayed {

    public static boolean running = true;
    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper;
    EmployeeServiceDao employeeServiceDao;
    EmployeeService employeeService;
    PositionServiceDao positionServiceDao;

    @Autowired
    public EmployeeMenuDisplay(EmployeeServiceDao employeeServiceDao, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeServiceDao = employeeServiceDao;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
        Clear.clearConsole();
        while (running) {
            System.out.println("   " + ANSI_YELLOW + "Меню сотрудников!" + ANSI_RESET);
            System.out.println("*" + "  " + ANSI_BLUE + "\u001B[4m" + "\u001B[1m" + "Выберите действие:" + ANSI_RESET);
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
                System.out.println(ANSI_RED + "\u001B[4m" + "Неверное значение" + ANSI_RESET);
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
                    new EmployeesByFilterDisplay(employeeServiceDao, employeeService).searchAll();
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
        try {
            System.out.println("Введите фамилию сотрудника:");
            String lastName = scanner.nextLine();

            System.out.println("Введите имя сотрудника:");
            String firstName = scanner.nextLine();

            System.out.println("Введите отчество сотрудника:");
            String middleName = scanner.nextLine();

            positionServiceDao.findAll();
            System.out.println("Выберите ID должности сотрудника:");
            Long positionId = scanner.nextLong();

            EmployeeRequest request = new EmployeeRequest(lastName, firstName, middleName, positionId);

            Employee employeeDTO = employeeService.create(request);
            System.out.println("Сотрудник успешно создан: " + objectMapper.writeValueAsString(employeeDTO));
            System.out.println("******************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
            running = false;
        }
    }

    private void modifyEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, данные которого нужно изменить:");
        Long id = scanner.nextLong();
        scanner.nextLine();
        try {
            EmployeeRequest request = new EmployeeRequest();
            request.setId(id);
            System.out.println("Введите новую фамилию сотрудника:");
            request.setLastName(scanner.nextLine());

            System.out.println("Введите новое имя сотрудника:");
            request.setFirstName(scanner.nextLine());

            System.out.println("Введите новое отчество сотрудника:");
            request.setMiddleName(scanner.nextLine());

            System.out.println("Введите ID должности для сотрудника:");
            request.setPositionId(scanner.nextLong());

            Employee employee = employeeService.update(id, request);
            System.out.println("Данные сотрудника успешно изменены.");
            System.out.println(objectMapper.writeValueAsString(employee));
            System.out.println("***********************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
            running = false;
        }
    }

    private void printEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, информацию о котором нужно вывести:");
        Long id = scanner.nextLong();
        scanner.nextLine();
        try {
            EmployeeRequest printEmployee = employeeService.getEmployeeById(id);
            System.out.println(objectMapper.writeValueAsString(printEmployee));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void printEmployeesSortedByLastName() throws JsonProcessingException {
//        try {
//            List<EmployeeDTO> employeeList = employeeServiceDaoImpl.sortedByLastName();
//            System.out.println(objectMapper.writeValueAsString(employeeList));
//        } catch (RuntimeException e) {
//            ExceptionHandler.handleException(e);
//        }
        System.out.println("I sortedByLastName");
    }

    private void terminateEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, которого нужно уволить:");
        Long id = scanner.nextLong();
        try {
            EmployeeRequest terminateEmployee = employeeService.terminate(id);
            System.out.println(objectMapper.writeValueAsString(terminateEmployee) + "Сотрудник успешно уволен.");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void terminatedEmployees() throws JsonProcessingException {
        try {
            EmployeeRequest employee = employeeService.terminated();
            System.out.println(objectMapper.writeValueAsString(employee));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
