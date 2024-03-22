package org.example.HR_ManagementSystem.entity;

import java.util.List;

public class Position {
    private static int nextId = 1;
    private final int id;
    private String name;

    public Position(int id, String name) {
        this.id = nextId++;
        this.name = name;
    }

    public Position(String name) {
        this.id = nextId++;
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

    public String toJsonString() {
        return "{ \"id\": " + id + ", \"name\": \"" + name + "\" }";
    }

    public List<Employee> getEmployees() {
        return null;
    }

    @Override
    public String toString() {
        return "Должность [ID: " + id + ", Название: " + name + "]";
    }
}
