package org.example.HR_ManagementSystem.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "position")
public class Position implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is null")
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Position(String name) {
        this.name = name;
    }

    public Position(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Position() {
    }

    public Long getId() {
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

    public void setId(Long id) {
        this.id = id;
    }
}