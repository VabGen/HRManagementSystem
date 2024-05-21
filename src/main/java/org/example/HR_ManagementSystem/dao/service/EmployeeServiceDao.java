package org.example.HR_ManagementSystem.dao.service;

import org.example.HR_ManagementSystem.dao.entity.Employee;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface EmployeeServiceDao {

    Employee create(Employee employee);

    List<Employee> read();

    Employee update(Employee employee);

    void delete(Long id);

    Employee findById(Long id);

    List<Employee> filter(EmployeeFilter filter, Pageable pageable);

    Employee terminate(Employee employee);
}

