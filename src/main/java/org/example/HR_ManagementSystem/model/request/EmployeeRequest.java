package org.example.HR_ManagementSystem.model.request;

public class EmployeeRequest {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long positionId;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String firstName, String middleName, String lastName, Long positionId) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.positionId = positionId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
}
