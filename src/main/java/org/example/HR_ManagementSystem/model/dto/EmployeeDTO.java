package org.example.HR_ManagementSystem.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.HR_ManagementSystem.source.model.Employee;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @JsonFormat(pattern = "dd.MM.yyyy HH.mm.ss")
    private ZonedDateTime creationDate;
    @JsonFormat(pattern = "dd.MM.yyyy HH.mm.ss")
    private ZonedDateTime modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private PositionDTO position;
    private int positionId;
    private boolean isTerminated;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, Instant creationDate, Instant modificationDate, String lastName, String firstName,
                       String middleName, PositionDTO position, int positionId, boolean isTerminated) {
        this.id = id;
        this.creationDate = creationDate.atZone(ZoneId.of("Europe/Moscow"));
        this.modificationDate = modificationDate.atZone(ZoneId.of("Europe/Moscow"));
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.positionId = positionId;
        this.isTerminated = isTerminated;
    }

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.creationDate = employee.getCreationDate().atZone(ZoneId.of("Europe/Moscow"));
        this.modificationDate = employee.getModificationDate().atZone(ZoneId.of("Europe/Moscow"));
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
//        this.positionId = employee.getPositionId();
        this.isTerminated = employee.isTerminated();
    }

    public Long getId() {
        return id;
    }

    public Long setId() {
        return id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ZonedDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(ZonedDateTime modificationDate) {
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

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }
}
