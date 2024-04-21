package org.example.HR_ManagementSystem.model.filter;

import java.time.ZonedDateTime;

public class EmployeeFilter {

    private String fio;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String positionName;
    private Boolean terminates;
    private Boolean sort;

    public EmployeeFilter() {
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Boolean getTerminates() {
        return terminates;
    }

    public void setTerminates(Boolean terminates) {
        this.terminates = terminates;
    }

    public Boolean getSort() {
        return sort;
    }

    public void setSort(Boolean sort) {
        this.sort = sort;
    }
}
