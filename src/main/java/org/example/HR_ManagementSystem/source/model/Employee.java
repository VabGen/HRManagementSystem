package org.example.HR_ManagementSystem.source.model;

import jakarta.persistence.*;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    private Instant creationDate;
    private Instant modificationDate;
    private String lastName;
    private String firstName;
    private String middleName;
    private boolean isTerminated;

    public Employee(String lastName, String firstName, String middleName, Position position) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.creationDate = Instant.now();
        this.modificationDate = Instant.now();
        this.isTerminated = false;
    }

//    public Employee(EmployeeRequest request) {
//        this.lastName = request.getLastName();
//        this.firstName = request.getFirstName();
//        this.middleName = request.getMiddleName();
//        this.position = request.getPosition();
//        this.isTerminated = false;
//    }

    public Employee() {

    }

    public Long getId() {
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

    public Integer getPosition() {
        return position.getId();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }
}