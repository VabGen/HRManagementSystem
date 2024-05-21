package org.example.HR_ManagementSystem.dao.service.impl;

import org.example.HR_ManagementSystem.dao.entity.Employee;
import org.example.HR_ManagementSystem.dao.repository.EmployeeRepository;
import org.example.HR_ManagementSystem.dao.service.EmployeeServiceDao;
import org.example.HR_ManagementSystem.dao.specifications.EmployeeSpecifications;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceDaoImpl implements EmployeeServiceDao {

    public static boolean running = true;
    private final EmployeeRepository repository;
    private final EmployeeSpecifications spec;

    public EmployeeServiceDaoImpl(EmployeeRepository repository, EmployeeSpecifications spec) {
        this.repository = repository;
        this.spec = spec;
    }

    @Override
    @Transactional
    public Employee create(Employee entity) {
        return repository.save(entity);
    }

    @Override
    public List<Employee> read() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Employee update(Employee entity) {
        return repository.save(entity);
    }

    @Override
    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Employee> filter(EmployeeFilter filter, Pageable pageable) {
        return repository.findAll(spec.filterByCriteria(filter).and(spec.withPosition()), pageable).getContent();
    }

    @Override
    public Employee terminate(Employee entity) {
        return repository.save(entity);
    }
}




