package org.example.HR_ManagementSystem.service;

import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementService {
    private final List<Employee> employees;
    private final List<Position> positions;

    public EmployeeManagementService() {
        employees = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public void createPosition(int id, String name) {
        for (Position position : positions) {
            if (position.getName().equals(name)) {
                System.out.println("Должность с таким именем уже существует.");
                return;
            }
        }
        Position newPosition = new Position(id, name);
        positions.add(newPosition);
        System.out.println("Должность успешно создана: " + newPosition);
    }


    public void createEmployee(String lastName, String firstName, String middleName, Position position) {
        Employee employee = new Employee(lastName, firstName, middleName, position);
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

    public void printEmployee(int id) {
        Employee employee = findEmployeeById(id);
        if (employee != null) {
            System.out.println(employee.toJsonString());
        } else {
            System.out.println("Сотрудник не найден.");
        }
    }

    public void printEmployeesSortedByLastName() {
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        List<Employee> sortedEmployees = new ArrayList<>(employees);
        sortedEmployees.sort((e1, e2) -> e1.getLastName().compareToIgnoreCase(e2.getLastName()));

        for (Employee employee : sortedEmployees) {
            System.out.println(employee.toJsonString());
        }
    }

    public void printEmployeesByFilter(String filter) {
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        for (Employee employee : employees) {
            if (employee.getLastName().contains(filter) || employee.getFirstName().contains(filter) ||
                    employee.getMiddleName().contains(filter) || employee.getPosition().getName().contains(filter)) {
                System.out.println(employee.toJsonString());
            }
        }
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
}
