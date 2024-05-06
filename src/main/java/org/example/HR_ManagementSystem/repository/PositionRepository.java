package org.example.HR_ManagementSystem.repository;

import org.example.HR_ManagementSystem.source.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    Optional<Position> findById(Long positionId);

    void deleteById(Long positionId);
}
