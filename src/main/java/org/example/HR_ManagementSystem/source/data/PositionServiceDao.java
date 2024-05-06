package org.example.HR_ManagementSystem.source.data;

import org.example.HR_ManagementSystem.source.model.Position;

import java.util.List;

public interface PositionServiceDao {
    Position findById(Long id);
    List<Position> findAll();
    void create(Position position);
    void delete(Position position);
    Position update(Long id, Position positionDetails);
}
