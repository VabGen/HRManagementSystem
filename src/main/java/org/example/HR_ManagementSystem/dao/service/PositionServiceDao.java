package org.example.HR_ManagementSystem.dao.service;

import org.example.HR_ManagementSystem.dao.entity.Position;

import java.util.List;

public interface PositionServiceDao {

    Position create(Position position);

    List<Position> read();

    Position update(Position employee);

    Position findById(Long id);

    void delete(Long id);

    List<Position> getPositionsWithEmployees();
}
