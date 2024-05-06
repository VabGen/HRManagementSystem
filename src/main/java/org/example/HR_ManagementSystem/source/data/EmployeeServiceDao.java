package org.example.HR_ManagementSystem.source.data;

import org.example.HR_ManagementSystem.source.model.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface EmployeeServiceDao {
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    void create(Employee employee);
    void delete(Employee employee);
    Employee update(Long id, Employee employeeDetails);
    Employee getEmployeeById(Long id);
}
