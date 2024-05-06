package org.example.HR_ManagementSystem.source.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "position")
    private List<Employee> employees;

    public Position(String name) {
        this.name = name;
    }

    public Position(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Position() {

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
}