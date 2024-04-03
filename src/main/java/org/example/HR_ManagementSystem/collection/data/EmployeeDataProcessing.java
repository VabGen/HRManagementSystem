package org.example.HR_ManagementSystem.collection.data;

import org.example.HR_ManagementSystem.collection.entity.Employee;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.collection.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.collection.service.PositionManagementService;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDataProcessing {
    public static boolean running = true;
    Scanner scanner = new Scanner(System.in);
    private final EmployeeManagementService employeeManagementService;
    private final PositionManagementService positionManagementService;

    public EmployeeDataProcessing() {
        this.employeeManagementService = EmployeeManagementService.getInstance();
        this.positionManagementService = PositionManagementService.getInstance();
    }

    public EmployeeDTO createEmployee(String lastName, String firstName, String middleName, int positionId) {
        Employee employee = new Employee(lastName, firstName, middleName, positionId);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        Optional<Position> position = PositionManagementService.getInstance().findPositionById(positionId);

        if (position.isEmpty()) {
            throw new RuntimeException();
        } else {
            employeeManagementService.createEmployee(employee);
            return employeeDTO;
        }
    }

    public EmployeeDTO modifyEmployee(int id) {
        Optional<Employee> optionalEmployee = employeeManagementService.printEmployee(id);

        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException();
        } else {
            Employee employee = optionalEmployee.get();
            EmployeeDTO employeeDTO = new EmployeeDTO(employee);
            employeeManagementService.modifyEmployee(employee);
            return employeeDTO;
        }
    }

    public EmployeeDTO printEmployee(int id) {
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isEmpty()) {
            throw new RuntimeException();
        } else {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.get());
            PositionManagementService positionManagementService = PositionManagementService.getInstance();
            Optional<Position> position = positionManagementService.findPositionById(employee.get().getPositionId());
            position.ifPresent(value -> employeeDTO.setPosition(new PositionDTO(value)));
            return employeeDTO;
        }
    }

    public List<EmployeeDTO> printEmployeesSortedByLastName() {
        List<Employee> employeeList = employeeManagementService.printEmployeesSortedByLastName();

        if (employeeList.isEmpty()) {
            throw new RuntimeException();
        }
        List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employeeList);
        return dtos;
    }

    public List<EmployeeDTO> searchByFullName(String fullName) {
        Map<Integer, Employee> employeeMapOptional = employeeManagementService.searchByFullName(fullName);
        List<Employee> employeeList = new ArrayList<>(employeeMapOptional.values());

        if (employeeList.isEmpty()) {
            throw new RuntimeException();
        } else {
            List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employeeList);
            return dtos;
        }
    }

    public EmployeeDTO terminateEmployee(int id) {
        Optional<Employee> employee = employeeManagementService.printEmployee(id);

        if (employee.isEmpty()) {
            throw new RuntimeException();
        } else {
            Employee terminateEmployee = employeeManagementService.terminateEmployee(employee.get());
            return new EmployeeDTO(terminateEmployee);
        }
    }

    public List<EmployeeDTO> terminatedEmployees() {
        List<Employee> employees = employeeManagementService.terminatedEmployees();

        if (employees.isEmpty()) {
            throw new RuntimeException();
        } else {
            List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employees);
            return dtos;
        }
    }

    public List<EmployeeDTO> searchByPosition(int positionId) {
        List<Employee> matchingEmployees = employeeManagementService.searchByPosition(positionId);
        List<EmployeeDTO> employeesWithPositions;

        if (matchingEmployees.isEmpty()) {
            throw new RuntimeException();
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

    private Instant validateDate() {
        boolean b = true;
        while (b) {
            try {
                b = false;
                return Instant.parse(scanner.nextLine());
            } catch (RuntimeException e) {
                ExceptionHandler.handleException(e);
            }
        }
        return null;
    }

    public List<EmployeeDTO> searchByCreationDate(Instant fromDate, Instant toDate) {
        List<Employee> employeeList = employeeManagementService.searchByCreationDate(fromDate, toDate);

        if (employeeList.isEmpty()) {
            throw new RuntimeException();
        } else {
            List<EmployeeDTO> dtos = new PositionDataProcessing().employeeListToDto(employeeList);
            return dtos;
        }
    }
}

