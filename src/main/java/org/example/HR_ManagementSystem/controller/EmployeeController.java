package org.example.HR_ManagementSystem.controller;

import org.example.HR_ManagementSystem.collection.data.EmployeeDataProcessing;
import org.example.HR_ManagementSystem.controller.request.EmployeeFilter;
import org.example.HR_ManagementSystem.controller.request.EmployeeRequest;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/employees")
public class EmployeeController {
    private final EmployeeDataProcessing employeeDataProcessing;

    public EmployeeController(EmployeeDataProcessing employeeDataProcessing) {
        this.employeeDataProcessing = employeeDataProcessing;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeDTO createdEmployee = employeeDataProcessing.createEmployee(employeeRequest);
        return ResponseEntity.ok(createdEmployee);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> modifyEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeDTO modifiedEmployee = employeeDataProcessing.modifyEmployee(employeeRequest);
        return ResponseEntity.ok(modifiedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> printEmployee(@PathVariable("id") int id) {
        EmployeeDTO employee = employeeDataProcessing.printEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesSortedByLastName() {
        List<EmployeeDTO> employees = employeeDataProcessing.printEmployeesSortedByLastName();
        return ResponseEntity.ok(employees);
    }
    @GetMapping("/search")
    public List<EmployeeDTO> getAll(EmployeeFilter filter) {
        return employeeDataProcessing.findAll(filter);
    }
    @GetMapping("/{id}/PositionSearch")
    public ResponseEntity<List<EmployeeDTO>> searchEmployeesByPosition(@PathVariable("id") int positionId) {
        List<EmployeeDTO> employees = employeeDataProcessing.searchByPosition(positionId);
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/{id}/terminate")
    public ResponseEntity<EmployeeDTO> terminateEmployee(@PathVariable("id") int id) {
        EmployeeDTO terminatedEmployee = employeeDataProcessing.terminateEmployee(id);
        return ResponseEntity.ok(terminatedEmployee);
    }


}
