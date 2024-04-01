package org.example.HR_ManagementSystem.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;
import org.example.HR_ManagementSystem.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.service.PositionManagementService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EmployeeDataProcessing {
    public static boolean running = true;
    Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper;
    private final EmployeeManagementService employeeManagementService;
    private final PositionManagementService positionManagementService;

    public EmployeeDataProcessing() {
        this.employeeManagementService = EmployeeManagementService.getInstance();
        this.positionManagementService = PositionManagementService.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public void createEmployees() throws JsonProcessingException {
        System.out.println("Введите фамилию сотрудника:");
        String lastName = scanner.nextLine();

        System.out.println("Введите имя сотрудника:");
        String firstName = scanner.nextLine();

        System.out.println("Введите отчество сотрудника:");
        String middleName = scanner.nextLine();

        PositionManagementService.getInstance().printAllPositions();
        System.out.println("Выберите ID должности сотрудника:");
        int positionId = scanner.nextInt();

        createEmployee(lastName, firstName, middleName, positionId);
    }

    public void createEmployee(String lastName, String firstName, String middleName, int positionId) throws JsonProcessingException {
        Employee employee = new Employee(lastName, firstName, middleName, positionId);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        Optional<Position> position = PositionManagementService.getInstance().findPositionById(positionId);

        if (position.isPresent()) {
            employeeManagementService.createEmployee(employee);
            System.out.println("Сотрудник успешно создан: " + objectMapper.writeValueAsString(employeeDTO));
            System.out.println("******************************************************");
        } else {
            System.out.println("Такой должности НЕТ!" + '\n' + "Сотрудник не создан!!!");
            System.out.println("******************************************************");
        }
        scanner.nextLine();
    }

    public void modifyEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, данные которого нужно изменить:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Optional<Employee> optionalEmployee = employeeManagementService.printEmployee(id);

        if (optionalEmployee.isEmpty()) {
            System.out.println("not found");
            return;
        }
        Employee employee = optionalEmployee.get();

        System.out.println("Введите новую фамилию сотрудника:");
        employee.setLastName(scanner.nextLine());

        System.out.println("Введите новое имя сотрудника:");
        employee.setFirstName(scanner.nextLine());

        System.out.println("Введите новое отчество сотрудника:");
        employee.setMiddleName(scanner.nextLine());

        System.out.println("Введите ID должности для сотрудника:");
        employee.setPositionId(scanner.nextInt());

        Employee employeeModify = employeeManagementService.modifyEmployee(employee);

        System.out.println("Данные сотрудника с ID №" + " " + employeeModify.getId() + " " + "успешно изменены.");
        System.out.println(objectMapper.writeValueAsString(employee));
        System.out.println("***********************************************************");
    }

    public void printEmployee() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника, информацию о котором нужно вывести:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isPresent()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.get());
            PositionManagementService positionManagementService = PositionManagementService.getInstance();
            Optional<Position> position = positionManagementService.findPositionById(employee.get().getPositionId());
            position.ifPresent(value -> employeeDTO.setPosition(new PositionDTO(value)));
            System.out.println(objectMapper.writeValueAsString(employeeDTO));
        } else {
            System.out.println("not found");
        }
    }

    public void printEmployeesSortedByLastName() throws JsonProcessingException {
        List<Employee> employeeList = employeeManagementService.printEmployeesSortedByLastName();

        if (employeeList.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }
        System.out.println(objectMapper.writeValueAsString(employeeList));
    }

    public void searchByFullName() throws JsonProcessingException {
        System.out.println("Введите данные для поиска сотрудников:");
        String fullName = scanner.nextLine();

        Map<Integer, Employee> employeeMapOptional = employeeManagementService.searchByFullName(fullName);

        if (employeeMapOptional.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
        } else {
            System.out.println(objectMapper.writeValueAsString(employeeMapOptional));
        }
    }

    public void terminateEmployee() {
        System.out.println("Введите ID сотрудника, которого нужно уволить:");
        int id = scanner.nextInt();
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isPresent()) {
            employeeManagementService.terminateEmployee(employee.get());
            System.out.println("Сотрудник с ID №" + id + " успешно уволен.");
        } else {
            System.out.println("Сотрудник с ID №" + id + " не найден.");
        }
    }

    public void terminatedEmployees() throws JsonProcessingException {
        List<Employee> employees = employeeManagementService.terminatedEmployees();

        if (!employees.isEmpty()) {
            System.out.println(objectMapper.writeValueAsString(employees));
        } else System.out.println("Уволенных сотрудников нет.");
    }

    public void searchByPosition() throws JsonProcessingException {
        System.out.println("Введите ID должности для поиска:");
        int positionId = scanner.nextInt();
        List<Employee> matchingEmployees = employeeManagementService.searchByPosition(positionId);
        scanner.nextLine();

        if (matchingEmployees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
        }

        List<String> positionNames = matchingEmployees.stream()
                .map(employee -> positionManagementService.findPositionById(employee.getPositionId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Position::getName)
                .toList();

        try {
            int numberOfPositions = positionNames.size();
            System.out.printf("Количество должностей под ID %d: %d%n", positionId, numberOfPositions);
            System.out.printf("Должность: %s%n", positionNames.get(positionId - 1));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Invalid position ID or position not found.");
        }

        List<Employee> employeesWithPositions = matchingEmployees.stream()
                .peek(employee -> {
                    Position position = positionManagementService.findPositionById(employee.getPositionId())
                            .orElseThrow(() -> new RuntimeException("Position not found"));
                    employee.setPosition(position);
                })
                .collect(Collectors.toList());

        System.out.println(objectMapper.writeValueAsString(employeesWithPositions));
    }

    private Instant validateDate() {
        boolean b = true;
        while (b) {
            try {
                b = false;
                return Instant.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Неверный формат даты");
            }
        }
        return null;
    }

    public void searchByCreationDate() throws JsonProcessingException {
        //2024-03-26T00:00:00Z
        System.out.println("Формат даты: \"yyyy-MM-ddTHH:mm:ssZ\"");
        System.out.println("Дата с:");
        Instant fromDate = validateDate();

        System.out.println("Дата по:");
        Instant toDate = validateDate();

        List<Employee> employeeList = employeeManagementService.searchByCreationDate(fromDate, toDate);
        System.out.println(objectMapper.writeValueAsString(employeeList));
    }
}

