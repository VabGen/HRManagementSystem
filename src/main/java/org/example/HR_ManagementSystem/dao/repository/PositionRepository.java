package org.example.HR_ManagementSystem.dao.repository;

import org.example.HR_ManagementSystem.dao.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position> {
}
