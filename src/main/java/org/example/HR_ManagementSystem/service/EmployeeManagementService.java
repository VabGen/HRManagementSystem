package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EmployeeManagementService extends PositionManagementService {
    private final List<Employee> employees;
    private static EmployeeManagementService employeeManagementServiceInstance;
    private final PositionManagementService positionManagementService;
    private final ObjectMapper objectMapper;
    Scanner scanner = new Scanner(System.in);

    private EmployeeManagementService() {
        this.positionManagementService = PositionManagementService.getInstance();
        employees = new ArrayList<>();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static EmployeeManagementService getInstance() {
        if (employeeManagementServiceInstance == null) {
            employeeManagementServiceInstance = new EmployeeManagementService();
        }
        return employeeManagementServiceInstance;
    }

    public void createEmployee(String lastName, String firstName, String middleName, int positionId) {
        Employee employee = new Employee(lastName, firstName, middleName, positionId);
        employees.add(employee);
    }

    public void modifyEmployee(int id, String lastName, String firstName, String middleName, Position position) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setMiddleName(middleName);
            employee.setPosition(position);
        }
    }

    public void printEmployee(int id) throws JsonProcessingException {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            Employee newEmployee = new Employee(employee);
            Position position = positionManagementService.findPositionById(newEmployee.getPositionId());
            newEmployee.setPosition(position);
            System.out.println(objectMapper.writeValueAsString(newEmployee));
        } else {
            System.out.println("Сотрудник не найден.");
        }
    }

    public void printEmployeesSortedByLastName() throws JsonProcessingException {
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        List<Employee> sortedEmployees = new ArrayList<>(employees);
        sortedEmployees.sort((e1, e2) -> e1.getLastName().compareToIgnoreCase(e2.getLastName()));
        System.out.println(objectMapper.writeValueAsString(sortedEmployees));
    }

    public void searchByFullName() throws JsonProcessingException {
        System.out.println("Введите данные для поиска сотрудников:");
        String fullName = scanner.nextLine();
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        List<Employee> list = new ArrayList<>();

        employees.forEach(e -> {
            boolean containsLastName = e.getLastName().contains(fullName);
            boolean containsFirstName = e.getFirstName().contains(fullName);
            boolean containsMiddleName = e.getMiddleName().contains(fullName);

            if (containsLastName || containsFirstName || containsMiddleName) {
                list.add(e);
            }
        });

        System.out.println(objectMapper.writeValueAsString(list));
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
        if (fromDate == null || toDate == null) {
            return;
        }
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        List<Employee> list = new ArrayList<>();

        for (Employee employee : employees) {
            if ((fromDate.isBefore(employee.getCreationDate()) || fromDate.equals(employee.getCreationDate())) &&
                    (toDate.isAfter(employee.getCreationDate()) || toDate.equals(employee.getCreationDate()))) {
                list.add(employee);
            }
        }
        System.out.println(objectMapper.writeValueAsString(list));
    }

    public void searchByPosition() throws JsonProcessingException {
        System.out.println("Введите ID сотрудника для поиска:");
        String positionSearch = scanner.nextLine();
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        List<Employee> list = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getPositionId() != null) {
                Position position = positionManagementService.findPositionById(employee.getPositionId());
                if (position.getName().contains(positionSearch)) {
                    list.add(employee);
                }
            }
        }
        System.out.println(objectMapper.writeValueAsString(list));
    }

    public void terminateEmployee(int id) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            employee.setTerminated(true);
        }
    }

    private Employee findEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> findAllByPositionId(Integer positionId) {
        return employees.stream().filter(e -> e.getPositionId().equals(positionId)).collect(Collectors.toList());
    }
}
