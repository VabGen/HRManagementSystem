package org.example.HR_ManagementSystem.collection.entity;

import org.example.HR_ManagementSystem.model.request.EmployeeRequest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Employee {
    private static int nextId = 1;
    private final int id;
    private Instant creationDate;
    private Instant modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private Integer positionId;
    private boolean isTerminated;

    public Employee(String lastName, String firstName, String middleName, int positionId) {
        this.id = nextId++;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.positionId = positionId;
        this.creationDate = Instant.now();
        this.modificationDate = Instant.now();
        this.isTerminated = false;
    }

    public Employee(EmployeeRequest request) {
        this.id = nextId++;
        this.lastName = request.getLastName();
        this.firstName = request.getFirstName();
        this.middleName = request.getMiddleName();
        this.positionId = request.getPositionId();
        this.isTerminated = false;
    }

    public int getId() {
        return id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = Instant.now().plus(3, ChronoUnit.HOURS);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }
}