package org.example.HR_ManagementSystem.service;

import org.example.HR_ManagementSystem.dao.entity.Employee;
import org.example.HR_ManagementSystem.dao.service.EmployeeServiceDao;
import org.example.HR_ManagementSystem.dto.EmployeeDto;
import org.example.HR_ManagementSystem.dto.mapper.EmployeeMapper;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeServiceDao employeeDAO;

    @Autowired
    public EmployeeService(EmployeeServiceDao employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto create(EmployeeRequest request) {
        Employee employee = EmployeeMapper.requestToEntity(request);
        Employee newEmployee = employeeDAO.create(employee);
        return EmployeeMapper.entityToDto(newEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> read() {
        List<Employee> employees = employeeDAO.read();
        return employees.stream().map(EmployeeMapper::entityToDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto update(EmployeeRequest request) {
        Employee existingEmployee = employeeDAO.findById(request.getId());
        if (existingEmployee == null) {
            throw new RuntimeException("Employee not found");
        }
        Employee autoCopyRequestToEntity = EmployeeMapper.copy(request, existingEmployee);
        Employee updatedEntity = employeeDAO.update(autoCopyRequestToEntity);
        return EmployeeMapper.entityToDto(updatedEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        employeeDAO.delete(id);
    }

    @Transactional
    public EmployeeDto findById(Long id) {
        Employee employee = employeeDAO.findById(id);
        return EmployeeMapper.entityToDto(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> filter(EmployeeFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Employee> employeePage = employeeDAO.filter(filter, pageable);
        return employeePage.stream().map(EmployeeMapper::entityToDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto terminate(Long id) {
        Employee employee = employeeDAO.findById(id);
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }
        employee.setTerminated(true);
        Employee updatedTerminate = employeeDAO.terminate(employee);
        return EmployeeMapper.entityToDto(updatedTerminate);
    }
}
