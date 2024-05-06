package org.example.HR_ManagementSystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.HR_ManagementSystem.model.request.PositionRequest;
import org.example.HR_ManagementSystem.source.data.PositionServiceDao;
import org.example.HR_ManagementSystem.source.model.Position;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    private final PositionServiceDao positionServiceDao;
    private final ModelMapper modelMapper;
    static boolean running = true;

    @Autowired
    public PositionService(PositionServiceDao positionServiceDao, ModelMapper modelMapper) {
        this.positionServiceDao = positionServiceDao;
        this.modelMapper = modelMapper;
    }

    public Position create(PositionRequest positionRequest) {
        Position newPosition = modelMapper.map(positionRequest, Position.class);
        positionServiceDao.create(newPosition);
        return newPosition;
    }

    public Position update(Long positionId, PositionRequest positionRequest) {
        Position existingPosition = positionServiceDao.findById(positionId);
        if (existingPosition != null) {
            modelMapper.map(positionRequest, existingPosition);
            return positionServiceDao.update(positionId, existingPosition);
        }
        throw new EntityNotFoundException("Position with ID " + positionId + " not found");
    }

    public void delete(Long positionId) {
        Position existingPosition = positionServiceDao.findById(positionId);
        if (existingPosition != null) {
            positionServiceDao.delete(existingPosition);
        } else {
            throw new EntityNotFoundException("Position with ID " + positionId + " not found");
        }
    }

    public Position getPositionById(Long positionId) {
        return positionServiceDao.findById(positionId);
    }

    public List<Position> getAllPositions() {
        return positionServiceDao.findAll();
    }

}
