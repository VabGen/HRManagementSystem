package org.example.HR_ManagementSystem.mapper;

import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.source.model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Employee convertToEntity(EmployeeRequest employeeRequest) {
        return modelMapper.map(employeeRequest, Employee.class);
    }

    public EmployeeRequest convertToEmployeeRequest(Employee employee) {
        return modelMapper.map(employee, EmployeeRequest.class);
    }
}
