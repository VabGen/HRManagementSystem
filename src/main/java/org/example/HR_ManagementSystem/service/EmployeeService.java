package org.example.HR_ManagementSystem.service;

import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.source.data.EmployeeServiceDao;
import org.example.HR_ManagementSystem.source.data.PositionServiceDao;
import org.example.HR_ManagementSystem.source.model.Employee;
import org.example.HR_ManagementSystem.source.model.Position;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeServiceDao employeeDAO;
    private final PositionServiceDao positionDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(@Qualifier("employeeServiceDao")EmployeeServiceDao employeeDAO, PositionServiceDao positionDAO, ModelMapper modelMapper) {
        this.employeeDAO = employeeDAO;
        this.positionDAO = positionDAO;
        this.modelMapper = modelMapper;
    }

    public Employee create(EmployeeRequest request) {
        Position position = positionDAO.findById(request.getPositionId());
        Employee employee = modelMapper.map(request, Employee.class);
        employee.setPosition(position);
        employeeDAO.create(employee);
        return employee;
    }

    public List<EmployeeRequest> getAllEmployees() {
        List<Employee> employees = employeeDAO.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeRequest.class))
                .collect(Collectors.toList());
    }

    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = employeeDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        modelMapper.map(request, employee);
        Position position = positionDAO.findById(request.getPositionId());
        employee.setPosition(position);
        employeeDAO.create(employee);
        return employee;
    }

    public void delete(Long id) {
        Employee employee = employeeDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeDAO.delete(employee);
    }

    public EmployeeRequest getEmployeeById(Long id) {
        Employee employee = employeeDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return modelMapper.map(employee, EmployeeRequest.class);
    }

    public EmployeeRequest terminate(Long id){
        System.out.println("I terminate" + id);
        return null;
    }

    public EmployeeRequest terminated(){
        System.out.println("I terminated");
        return null;
    }

    public List<EmployeeRequest> findAll(EmployeeFilter filter){
        System.out.println("I terminated" + filter);
        return null;
    }
}