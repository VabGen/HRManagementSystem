package org.example.HR_ManagementSystem.collection.data;

import org.example.HR_ManagementSystem.collection.entity.Employee;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.collection.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.collection.service.PositionManagementService;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.model.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.model.dto.PositionDTO;
import org.example.HR_ManagementSystem.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class EmployeeDataProcessing {
    public static boolean running = true;
    private final EmployeeManagementService employeeManagementService;

    public EmployeeDataProcessing() {
        this.employeeManagementService = EmployeeManagementService.getInstance();
    }

    public EmployeeDTO create(EmployeeRequest request) {
        Employee employee = new Employee(request);
        employee.setCreationDate(Instant.now());
        employee.setModificationDate(Instant.now());
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        if (request.getPositionId() != null) {
            Optional<Position> position = PositionManagementService.getInstance().findPositionById(request.getPositionId());
            if (position.isEmpty()) {
                throw new BadRequestException("Employee not found");
            }
        }
        employeeManagementService.createEmployee(employee);
        return employeeDTO;
    }

    public EmployeeDTO modify(EmployeeRequest request) {
        Optional<Employee> optionalEmployee = employeeManagementService.printEmployee(request.getId());

        if (optionalEmployee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        employee.setLastName(request.getLastName());
        employee.setFirstName(request.getFirstName());
        employee.setMiddleName(request.getMiddleName());
        employee.setPositionId(request.getPositionId());
        employee.setModificationDate(Instant.now());
        return new EmployeeDTO(employee);
    }

    public EmployeeDTO print(int id) {
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        } else {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.get());
            PositionManagementService positionManagementService = PositionManagementService.getInstance();
            Optional<Position> position = positionManagementService.findPositionById(employee.get().getPositionId());
            position.ifPresent(value -> employeeDTO.setPosition(new PositionDTO(value)));
            return employeeDTO;
        }
    }

    public List<EmployeeDTO> findAll(EmployeeFilter filter) {
        List<EmployeeDTO> dtoList = new ArrayList<>();
        employeeManagementService.findAll(filter).forEach(e -> dtoList.add(new EmployeeDTO(e)));
        return dtoList;
    }

    public List<EmployeeDTO> sortedByLastName() {
        List<Employee> employeeList = employeeManagementService.printEmployeesSortedByLastName();

        if (employeeList.isEmpty()) {
            throw new BadRequestException("Employee not found");
        }
        List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employeeList);
        return dtos;
    }

    public EmployeeDTO terminate(int id) {
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        } else {
            Employee terminateEmployee = employeeManagementService.terminateEmployee(employee.get());
            return new EmployeeDTO(terminateEmployee);
        }
    }

    public List<EmployeeDTO> terminated() {
        List<Employee> employees = employeeManagementService.terminatedEmployees();

        if (employees.isEmpty()) {
            throw new BadRequestException("Employee not found");
        } else {
            List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employees);
            return dtos;
        }
    }
}

