package org.example.HR_ManagementSystem.controllers;

import org.example.HR_ManagementSystem.dto.EmployeeDto;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.create(request));
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> update(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeDto updatedEmployee = employeeService.update(employeeRequest);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> get(@PathVariable Long id) {
        EmployeeDto findEmployee = employeeService.findById(id);
        return ResponseEntity.ok(findEmployee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> filter(@ModelAttribute EmployeeFilter filter,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "1000") int size) {
        List<EmployeeDto> filterEmployee = employeeService.filter(filter, page, size);
        return ResponseEntity.ok(filterEmployee);
    }
}



