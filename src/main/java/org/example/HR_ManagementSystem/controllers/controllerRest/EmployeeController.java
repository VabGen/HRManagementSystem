package org.example.HR_ManagementSystem.controllers.controllerRest;

import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.service.EmployeeService;
import org.example.HR_ManagementSystem.source.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.create(employeeRequest);
    }

    @PutMapping("/{Id}")
    public Employee updateEmployee(@PathVariable Long Id, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(Id, employeeRequest);
    }

    @DeleteMapping("/{Id}")
    public void deleteEmployee(@PathVariable Long Id) {
        employeeService.delete(Id);
    }

    @GetMapping("/{Id}")
    public EmployeeRequest getEmployeeById(@PathVariable Long Id) {
        return employeeService.getEmployeeById(Id);
    }

    @GetMapping
    public List<EmployeeRequest> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}

