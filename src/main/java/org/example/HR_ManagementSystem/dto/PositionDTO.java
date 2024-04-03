package org.example.HR_ManagementSystem.dto;

import org.example.HR_ManagementSystem.collection.entity.Position;

import java.util.List;

public class PositionDTO {
    private int id;
    private String name;
    private List<EmployeeDTO> employees;

    public PositionDTO(Position position) {
        this.id = position.getId();
        this.name = position.getName();
//        List<Employee> employees1 = position.getEmployees();
//        employees1.forEach(e -> employees.add(new EmployeeDTO(e)));
    }

    public PositionDTO(int id, String name, List<EmployeeDTO> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
}
