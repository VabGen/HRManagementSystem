package org.example.HR_ManagementSystem.entity;

import java.util.Date;

public class Employee {
    private static int nextId = 1;

    private final int id;
    private final Date creationDate;
    private Date modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private Position position;
    private Position positionId;
    private boolean isTerminated;

    public Employee(String lastName, String firstName, String middleName, Position position) {
        this.id = nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.position = position;
        this.creationDate = new Date();
        this.modificationDate = new Date();
        this.isTerminated = false;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Position getPosition() {
        return positionId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.modificationDate = new Date();
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }

    public String toJsonString() {
        return "{ \"id\": " + id + ", \"creationDate\": \"" + creationDate + "\", \"modificationDate\": \"" +
                modificationDate + "\", \"lastName\": \"" + lastName + "\", \"firstName\": \"" + firstName +
                "\", \"middleName\": \"" + middleName + "\", \"position\": \"" + position.getName() +
                "\", \"isTerminated\": " + isTerminated + " }";
    }
}







