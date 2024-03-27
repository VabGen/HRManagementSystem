package org.example.HR_ManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Employee {
    private static int nextId = 1;
    private final int id;
    @JsonFormat(pattern = "dd.MM.yyyy HH.mm.ss", timezone = "UTC")
    private Instant creationDate;
    @JsonFormat(pattern = "dd.MM.yyyy HH.mm.ss", timezone = "UTC")
    private Instant modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private Position position;
    private Integer positionId;
    private boolean isTerminated;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public Employee(Employee employee) {
        this.id = employee.getId();
        this.creationDate = employee.getCreationDate();
        this.modificationDate = employee.getModificationDate();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.position = employee.getPosition();
        this.positionId = employee.getPositionId();
        this.isTerminated = employee.isTerminated;
    }

    public Employee(String lastName, String firstName, String middleName, int positionId) {
        this.id = nextId++;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.positionId = positionId;
        this.creationDate = Instant.now().plus(3, ChronoUnit.HOURS);
        this.modificationDate = Instant.now().plus(3, ChronoUnit.HOURS);
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
        this.modificationDate = modificationDate;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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







