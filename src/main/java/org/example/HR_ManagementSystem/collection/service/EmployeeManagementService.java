package org.example.HR_ManagementSystem.collection.service;

import org.example.HR_ManagementSystem.collection.entity.Employee;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.controller.request.EmployeeFilter;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeManagementService {
    private final Map<Integer, Employee> employees;
    private static EmployeeManagementService employeeManagementServiceInstance;
    private final PositionManagementService positionManagementService;
    private final boolean initData = true;

    public EmployeeManagementService() {
        this.positionManagementService = PositionManagementService.getInstance();
        employees = new HashMap<>();

        if (initData) {
            List<Employee> employeeList = Arrays.asList(
                    new Employee("Ivanov", "Ivan", "Ivanovich", 1),
                    new Employee("Petrov", "Petr", "Petrovich", 2),
                    new Employee("Sidorov", "Alexey", "Igorevich", 3)
            );
            employeeList.forEach(e -> employees.put(e.getId(), e));
        }
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

    public Optional<Employee> printEmployee(int id) {
        return findEmployeeById(id);
    }


    public List<Employee> findAll(EmployeeFilter filter) {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : employees.values()) {
            boolean fio = filter.getFio() == null ||
                    (employee.getLastName().toLowerCase().contains(filter.getFio().toLowerCase()) ||
                            employee.getFirstName().toLowerCase().contains(filter.getFio().toLowerCase()) ||
                            employee.getMiddleName().toLowerCase().contains(filter.getFio().toLowerCase()));

            boolean date = filter.getStartDate() == null || filter.getEndDate() == null ||
                    (filter.getStartDate().isBefore(employee.getCreationDate().atZone(ZoneId.of("Europe/Moscow"))) ||
                            filter.getStartDate().equals(employee.getCreationDate().atZone(ZoneId.of("Europe/Moscow")))) &&
                            (filter.getEndDate().isAfter(employee.getCreationDate().atZone(ZoneId.of("Europe/Moscow"))) ||
                                    filter.getEndDate().equals(employee.getCreationDate().atZone(ZoneId.of("Europe/Moscow"))));

            boolean position = true;
            if (filter.getPositionName() != null) {
                if (employee.getPositionId() == null) {
                    position = false;
                } else {
                    Optional<Position> positionById = positionManagementService.findPositionById(employee.getPositionId());
                    if (positionById.isPresent()) {
                        if (!positionById.get().getName().contains(filter.getPositionName())) {
                            position = false;
                        }
                    }
                }
                if (!positionManagementService.isPositionExists(filter.getPositionName())) {
                    position = false;
                }
            }
            boolean terminate = filter.getTerminates() == null ||
                    employee.isTerminated() == filter.getTerminates();

            if (fio && date && position && terminate) {
                employeeList.add(employee);
            }
        }
        if (filter.getSort() != null) {
            return employeeList.stream()
                    .sorted(Comparator.comparing(Employee::getLastName))
                    .collect(Collectors.toList());
        }
        return employeeList;
    }

    public List<Employee> printEmployeesSortedByLastName() {
        return employees.values().stream()
                .filter(employee -> !employee.isTerminated())
                .sorted(Comparator.comparing(Employee::getLastName, Comparator.comparing(String::toLowerCase)))
                .collect(Collectors.toList());
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
