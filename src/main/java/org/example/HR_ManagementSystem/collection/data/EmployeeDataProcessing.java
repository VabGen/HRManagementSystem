package org.example.HR_ManagementSystem.collection.data;

import org.example.HR_ManagementSystem.collection.entity.Employee;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.collection.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.collection.service.PositionManagementService;
import org.example.HR_ManagementSystem.controller.request.EmployeeFilter;
import org.example.HR_ManagementSystem.controller.request.EmployeeRequest;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.example.HR_ManagementSystem.exception.BadRequestException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDataProcessing {
    public static boolean running = true;
    private final EmployeeManagementService employeeManagementService;
    private final PositionManagementService positionManagementService;

    public EmployeeDataProcessing() {
        this.employeeManagementService = EmployeeManagementService.getInstance();
        this.positionManagementService = PositionManagementService.getInstance();
    }

    public EmployeeDTO createEmployee(EmployeeRequest request) {
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

    public EmployeeDTO modifyEmployee(EmployeeRequest request) {
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

    public EmployeeDTO printEmployee(int id) {
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

    public List<EmployeeDTO> printEmployeesSortedByLastName() {
        List<Employee> employeeList = employeeManagementService.printEmployeesSortedByLastName();

        if (employeeList.isEmpty()) {
            throw new BadRequestException("Employee not found");
        }
        List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employeeList);
        return dtos;
    }

//    public List<EmployeeDTO> searchByFullName(String fullName) {
//        Map<Integer, Employee> employeeMapOptional = employeeManagementService.searchByFullName(fullName);
//        List<Employee> employeeList = new ArrayList<>(employeeMapOptional.values());
//
//        if (employeeList.isEmpty()) {
//            throw new BadRequestException("Employee not found");
//        } else {
//            List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employeeList);
//            return dtos;
//        }
//    }

    public EmployeeDTO terminateEmployee(int id) {
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isEmpty()) {
            throw new BadRequestException("Employee not found");
        } else {
            Employee terminateEmployee = employeeManagementService.terminateEmployee(employee.get());
            return new EmployeeDTO(terminateEmployee);
        }
    }

    public List<EmployeeDTO> terminatedEmployees() {
        List<Employee> employees = employeeManagementService.terminatedEmployees();

        if (employees.isEmpty()) {
            throw new BadRequestException("Employee not found");
        } else {
            List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employees);
            return dtos;
        }
    }

    public List<EmployeeDTO> searchByPosition(int positionId) {
        List<Employee> matchingEmployees = employeeManagementService.searchByPosition(positionId);
        List<EmployeeDTO> employeesWithPositions;

        if (matchingEmployees.isEmpty()) {
            throw new BadRequestException("Employee not found");
        } else {
            List<String> positionNames = matchingEmployees.stream()
                    .map(employee -> positionManagementService.findPositionById(employee.getPositionId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(Position::getName)
                    .toList();

            employeesWithPositions = new PositionDataProcessing().employeeListToDto(matchingEmployees);
            employeesWithPositions.stream()
                    .peek(employeeDTO -> {
                        Position position = positionManagementService.findPositionById(employeeDTO.getPositionId())
                                .orElseThrow(() -> new RuntimeException("Position not found"));
                        employeeDTO.setPosition(new PositionDTO(position));
                    })
                    .collect(Collectors.toList());
        }
        return employeesWithPositions;
    }
}

