package org.example.HR_ManagementSystem.controller;

import org.example.HR_ManagementSystem.collection.data.EmployeeDataProcessing;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.model.dto.EmployeeDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/employees")
public class EmployeeController {
    private final EmployeeDataProcessing employeeDataProcessing;

    @Autowired
    public EmployeeController(EmployeeDataProcessing employeeDataProcessing) {
        this.employeeDataProcessing = employeeDataProcessing;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeDTO createdEmployee = employeeDataProcessing.create(employeeRequest);
        return ResponseEntity.ok(createdEmployee);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> modifyEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeDTO modifiedEmployee = employeeDataProcessing.modify(employeeRequest);
        return ResponseEntity.ok(modifiedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> printEmployee(@PathVariable("id") int id) {
        EmployeeDTO employee = employeeDataProcessing.print(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public List<EmployeeDTO> getAll(EmployeeFilter filter) {
        return employeeDataProcessing.findAll(filter);
    }

    @PostMapping("/{id}")
    public ResponseEntity<EmployeeDTO> terminateEmployee(@PathVariable("id") int id) {
        EmployeeDTO terminatedEmployee = employeeDataProcessing.terminate(id);
        return ResponseEntity.ok(terminatedEmployee);
    }
}
