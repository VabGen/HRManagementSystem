package org.example.HR_ManagementSystem.source.data.impl;

import org.example.HR_ManagementSystem.source.data.EmployeeServiceDao;
import org.example.HR_ManagementSystem.source.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeServiceDaoImpl implements EmployeeServiceDao {

    public static boolean running = true;
    private final SessionFactory sessionFactory;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceDaoImpl(SessionFactory sessionFactory, ModelMapper modelMapper) {
        this.sessionFactory = sessionFactory;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Employee.class, id));
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        }
    }

    @Override
    public void create(Employee employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot save employee", e);
        }
    }

    @Override
    public void delete(Employee employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot delete employee", e);
        }
    }

    @Override
    public Employee update(Long id, Employee employeeDetails) {
        Transaction transaction = null;
        Employee existingEmployee = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            existingEmployee = session.get(Employee.class, id);
            if (existingEmployee != null) {
                modelMapper.map(employeeDetails, existingEmployee);
                session.update(existingEmployee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot update employee", e);
        }
        return existingEmployee;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Employee.class, id);
        }
    }
}


