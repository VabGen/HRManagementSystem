package org.example.HR_ManagementSystem.model.request;

public class EmployeeRequest {
    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Integer positionId;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String lastName, String firstName, String middleName, int positionId) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.positionId = positionId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
}
