package org.example.HR_ManagementSystem.entity;

import java.util.List;
import java.util.function.Supplier;

public class Position implements Supplier {
    private static int nextId = 1;
    private final int id;
    private String name;
    private List<Employee> employees;

    public Position(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public Position(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public Object get() {
        return null;
    }
}
