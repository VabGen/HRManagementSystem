package org.example.HR_ManagementSystem.service;

import org.example.HR_ManagementSystem.dao.entity.Position;
import org.example.HR_ManagementSystem.dao.service.PositionServiceDao;
import org.example.HR_ManagementSystem.dto.PositionDto;
import org.example.HR_ManagementSystem.dto.mapper.PositionMapper;
import org.example.HR_ManagementSystem.model.request.PositionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {
    private final PositionServiceDao positionDAO;

    @Autowired
    public PositionService(PositionServiceDao positionDAO) {
        this.positionDAO = positionDAO;
    }

    @Transactional(rollbackFor = Exception.class)
    public PositionDto create(PositionRequest request) {
        Position position = PositionMapper.requestToEntity(request);
        Position newPosition = positionDAO.create(position);
        return PositionMapper.entityToDto(newPosition);
    }

    @Transactional(readOnly = true)
    public List<PositionDto> read() {
        List<Position> position = positionDAO.read();
        return position.stream().map(PositionMapper::entityToDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public PositionDto update(PositionRequest request) {
        Position existingPosition = positionDAO.findById(request.getId());
        if (existingPosition == null) {
            throw new RuntimeException("Position not found");
        }
        Position autoCopyRequestToPosition = PositionMapper.copy(request, existingPosition);
        Position updatedPosition = positionDAO.update(autoCopyRequestToPosition);
        return PositionMapper.entityToDto(updatedPosition);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        positionDAO.delete(id);
    }

    @Transactional
    public PositionDto findById(Long id) {
        Position position = positionDAO.findById(id);
        return PositionMapper.entityToDto(position);
    }

    @Transactional(readOnly = true)
    public List<PositionDto> getPositionsWithEmployees() {
        List<Position> position = positionDAO.getPositionsWithEmployees();
        return position.stream().map(PositionMapper::entityToDto).collect(Collectors.toList());
    }
}
