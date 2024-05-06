package org.example.HR_ManagementSystem.repository;

import org.example.HR_ManagementSystem.source.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
