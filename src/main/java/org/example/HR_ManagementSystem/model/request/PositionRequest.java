package org.example.HR_ManagementSystem.model.request;

public class PositionRequest {

    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
