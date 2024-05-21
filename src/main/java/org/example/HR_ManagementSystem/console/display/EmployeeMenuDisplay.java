package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.console.Clear;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.example.HR_ManagementSystem.console.MenuDisplayed;
import org.example.HR_ManagementSystem.dao.service.EmployeeServiceDao;
import org.example.HR_ManagementSystem.dao.service.PositionServiceDao;
import org.example.HR_ManagementSystem.dto.EmployeeDto;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class EmployeeMenuDisplay extends MenuDisplayed {

    public static boolean running = true;
    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper;
    EmployeeServiceDao employeeServiceDao;
    EmployeeService employeeService;
    PositionServiceDao positionServiceDao;
    EmployeesByFilterDisplay employeesByFilterDisplay;
    MenuDisplay menuDisplay;

    @Autowired
    public EmployeeMenuDisplay(EmployeeServiceDao employeeServiceDao, @Lazy MenuDisplay menuDisplay,
                               EmployeeService employeeService, PositionServiceDao positionServiceDao,
                               EmployeesByFilterDisplay employeesByFilterDisplay) {
        this.employeeService = employeeService;
        this.employeeServiceDao = employeeServiceDao;
        this.positionServiceDao = positionServiceDao;
        this.employeesByFilterDisplay = employeesByFilterDisplay;
        this.menuDisplay = menuDisplay;
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
            System.out.println("2. Удалить сотрудника");
            System.out.println("3. Изменить данные сотрудника");
            System.out.println("4. Вывести информацию о сотруднике");
            System.out.println("5. Вывести список сотрудников, отсортированный по фамилии");
            System.out.println("6. Вывести список сотрудников по фильтру");
            System.out.println("7. Уволить сотрудника");
            System.out.println("8. Уволенные сотрудники");
            System.out.println("9. Назад к основному меню");

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
                    create();
                    break;
                case 2:
                    Clear.clearConsole();
                    delete();
                    break;
                case 3:
                    Clear.clearConsole();
                    update();
                    break;
                case 4:
                    Clear.clearConsole();
                    findById();
                    break;
                case 5:
                    Clear.clearConsole();
                    sort();
                    break;
                case 6:
                    Clear.clearConsole();
                    employeesByFilterDisplay.searchAll();
                    break;
                case 7:
                    Clear.clearConsole();
                    updateTerminateStatus();
                    break;
                case 8:
                    Clear.clearConsole();
                    terminatedEmployees();
                    break;
                case 9:
                    Clear.clearConsole();
                    menuDisplay.doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
                    break;
            }
        }
    }

    private void create() throws JsonProcessingException {
        try {
            System.out.println("Введите фамилию сотрудника:");
            String lastName = scanner.nextLine();

            System.out.println("Введите имя сотрудника:");
            String firstName = scanner.nextLine();

            System.out.println("Введите отчество сотрудника:");
            String middleName = scanner.nextLine();

            positionServiceDao.read();

            System.out.println("Выберите ID должности сотрудника:");
            Long positionId = scanner.nextLong();

            EmployeeRequest request = new EmployeeRequest(lastName, firstName, middleName, positionId);

            EmployeeDto employeeDTO = employeeService.create(request);
            System.out.println("Сотрудник успешно создан: " + objectMapper.writeValueAsString(employeeDTO));
            System.out.println("******************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
            running = false;
        }
    }

    private void update() throws JsonProcessingException {
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

            EmployeeDto employee = employeeService.update(request);
            System.out.println("Данные сотрудника успешно изменены.");
            System.out.println(objectMapper.writeValueAsString(employee));
            System.out.println("***********************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
            running = false;
        }
    }

    private void findById() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, информацию о котором нужно вывести:");
        Long id = scanner.nextLong();
        try {
            EmployeeDto printEmployee = employeeService.findById(id);
            System.out.println(objectMapper.writeValueAsString(printEmployee));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void sort() throws JsonProcessingException {
        EmployeeFilter filter = new EmployeeFilter();
        filter.getSort();
        try {
            List<EmployeeDto> employeeList = employeeService.filter(filter, 0, 30);
            for (EmployeeDto employee : employeeList) {
                System.out.println(objectMapper.writeValueAsString(employee));
            }
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void updateTerminateStatus() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, которого нужно уволить:");
        Long id = scanner.nextLong();
        try {
            EmployeeDto terminateEmployee = employeeService.terminate(id);
            System.out.println(objectMapper.writeValueAsString(terminateEmployee) + "Сотрудник успешно уволен.");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void terminatedEmployees() throws JsonProcessingException {
        EmployeeFilter employeeFilter = new EmployeeFilter();
        employeeFilter.setTerminates(true);
        try {
            List<EmployeeDto> employeeList = employeeService.filter(employeeFilter, 0, 30);
            System.out.println(objectMapper.writeValueAsString(employeeList));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void delete() {
        try {
            System.out.print("Введите ID сотрудника для удаления:");
            Long id = scanner.nextLong();
            employeeService.delete(id);
            System.out.println("Сотрудник успешно удален.");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
