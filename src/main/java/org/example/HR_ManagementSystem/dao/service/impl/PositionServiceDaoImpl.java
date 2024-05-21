package org.example.HR_ManagementSystem.dao.service.impl;

import org.example.HR_ManagementSystem.dao.entity.Position;
import org.example.HR_ManagementSystem.dao.repository.PositionRepository;
import org.example.HR_ManagementSystem.dao.service.PositionServiceDao;
import org.example.HR_ManagementSystem.dao.specifications.PositionSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PositionServiceDaoImpl implements PositionServiceDao {

    private final PositionRepository repository;
    private final PositionSpecifications spec;

    @Autowired
    public PositionServiceDaoImpl(PositionRepository repository, PositionSpecifications spec) {
        this.repository = repository;
        this.spec = spec;
    }

    @Override
    @Transactional
    public Position create(Position position) {
        return repository.save(position);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> read() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Position update(Position position) {
        return repository.save(position);
    }

    @Override
    public Position findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Position not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> getPositionsWithEmployees() {
        return repository.findAll(spec.withEmployees());
    }
}