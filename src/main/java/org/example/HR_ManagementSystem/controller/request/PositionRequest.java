package org.example.HR_ManagementSystem.controller.request;

public class PositionRequest {
    private Integer id;
    private String name;

    public PositionRequest() {
    }

    public PositionRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}