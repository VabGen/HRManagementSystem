package org.example.HR_ManagementSystem.dto.mapper;

import org.example.HR_ManagementSystem.dao.entity.Employee;
import org.example.HR_ManagementSystem.dto.EmployeeDto;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static EmployeeDto mapEmployeeToDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setLastName(employee.getLastName());
        dto.setTerminated(employee.isTerminated());
        dto.setPositionId(employee.getPositionId());
        dto.setCreationDate(employee.getCreationDate());
        dto.setModificationDate(employee.getModificationDate());
        return dto;
    }

    public static EmployeeDto entityToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeDto employeeDto = mapEmployeeToDto(employee);
        employeeDto.setPosition(PositionMapper.entityToDto(employee.getPosition()));

        return employeeDto;
    }

    public static Employee requestToEntity(EmployeeRequest request) {
        if (request == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(request.getId());
        employee.setFirstName(request.getFirstName());
        employee.setMiddleName(request.getMiddleName());
        employee.setLastName(request.getLastName());
        employee.setPositionId(request.getPositionId());
        return employee;
    }

    public static List<EmployeeDto> entityToDto(List<Employee> employees) {

        List<EmployeeDto> list = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = mapEmployeeToDto(employee);
            list.add(employeeDto);
        }

        return list;
    }

    public static Employee copy(EmployeeRequest request, Employee employee) {
        employee.setFirstName(request.getFirstName());
        employee.setMiddleName(request.getMiddleName());
        employee.setLastName(request.getLastName());
        employee.setPositionId(request.getPositionId());
        return employee;
    }
}
