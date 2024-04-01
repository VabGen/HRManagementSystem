package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeManagementService {
    private final Map<Integer, Employee> employees;
    private static EmployeeManagementService employeeManagementServiceInstance;
    private final PositionManagementService positionManagementService;
    private final ObjectMapper objectMapper;
    private final boolean initData = true;

    private EmployeeManagementService() {
        this.positionManagementService = PositionManagementService.getInstance();
        employees = new HashMap<>();

        if (initData) {
            List<Employee> employeeList = Arrays.asList(
                    new Employee("l", "f", "m", 1),
                    new Employee("l", "f", "m", 2),
                    new Employee("l", "f", "m", 3)
            );
            employeeList.forEach(e -> employees.put(e.getId(), e));
        }
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

    public void createEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public Employee modifyEmployee(Employee employee) {
        employee.setModificationDate(Instant.now());
        return employee;
    }

    public Optional<Employee> printEmployee(int id) {
        return findEmployeeById(id);
    }

    public List<Employee> printEmployeesSortedByLastName() {
        List<Employee> sortedEmployees = employees.values().stream()
                .filter(employee -> !employee.isTerminated())
                .sorted(Comparator.comparing(Employee::getLastName, Comparator.comparing(String::toLowerCase)))
                .collect(Collectors.toList());
        return sortedEmployees;
    }

    public Map<Integer, Employee> searchByFullName(String fullName) {
        Map<Integer, Employee> matchingEmployees = new HashMap<>();
        employees.forEach((id, employee) -> {

            if (!employee.isTerminated()) {
                boolean containsLastName = employee.getLastName().contains(fullName);
                boolean containsFirstName = employee.getFirstName().contains(fullName);
                boolean containsMiddleName = employee.getMiddleName().contains(fullName);

                if (containsLastName || containsFirstName || containsMiddleName) {
                    matchingEmployees.put(id, employee);
                }
            }
        });
        return matchingEmployees;
    }

    public List<Employee> searchByCreationDate(Instant fromDate, Instant toDate) {
        List<Employee> matchingEmployees = new ArrayList<>();

        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            Instant creationDate = employee.getCreationDate();

            if (!employee.isTerminated() && (fromDate == null || fromDate.isBefore(creationDate) || fromDate.equals(creationDate)) &&
                    (toDate == null || toDate.isAfter(creationDate) || toDate.equals(creationDate))) {
                matchingEmployees.add(employee);
            }
        }
        return matchingEmployees;
    }

    public List<Employee> searchByPosition(Integer positionId) {
        List<Employee> matchingEmployees = employees.values().stream()
                .filter(employee -> !employee.isTerminated())
                .filter(employee -> employee.getPositionId() != null)
                .filter(employee -> positionManagementService.findPositionById(employee.getPositionId())
                        .map(Position::getId)
                        .filter(id -> id.equals(positionId))
                        .isPresent())
                .collect(Collectors.toList());
        return matchingEmployees;
    }

    public Employee terminateEmployee(Employee employee) {
        employee.setTerminated(true);
        return employee;
    }

    public List<Employee> terminatedEmployees() {
        List<Employee> terminatedEmployees = new ArrayList<>();
        for (Employee employee : employees.values()) {
            if (employee.isTerminated()) {
                terminatedEmployees.add(employee);
            }
        }
        return terminatedEmployees;
    }

    public Optional<Employee> findEmployeeById(int id) {
        Employee employee = employees.get(id);
        return Optional.ofNullable(employee);
    }

    public List<Employee> findAllByPositionId(Integer positionId) {
        Map<Integer, Employee> matchingEmployees = new HashMap<>();
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {

            if (entry.getValue().getPositionId() != null && entry.getValue().getPositionId().equals(positionId)) {
                matchingEmployees.put(entry.getKey(), entry.getValue());
            }
        }
        return matchingEmployees.values().stream().toList();
    }
}
