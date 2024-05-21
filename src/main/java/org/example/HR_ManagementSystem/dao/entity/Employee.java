package org.example.HR_ManagementSystem.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_id")
    private Long positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private Position position;

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant modificationDate;

    @NotNull(message = "name is null")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "name is null")
    @Column(name = "middle_name")
    private String middleName;

    @NotNull(message = "name is null")
    @Column(name = "last_name")
    private String lastName;

    @Column(columnDefinition = "bool default false")
    private boolean terminated;

    public Employee(String lastName, String firstName, String middleName, Position position) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.creationDate = Instant.now();
        this.modificationDate = Instant.now();
        this.terminated = false;
    }

    public Employee() {
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
}