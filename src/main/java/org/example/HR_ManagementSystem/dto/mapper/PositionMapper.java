package org.example.HR_ManagementSystem.dto.mapper;

import org.example.HR_ManagementSystem.dao.entity.Position;
import org.example.HR_ManagementSystem.dto.PositionDto;
import org.example.HR_ManagementSystem.model.request.PositionRequest;

public class PositionMapper {

    public static PositionDto mapPositionToDto(Position position) {
        PositionDto dto = new PositionDto();
        dto.setId(position.getId());
        dto.setName(position.getName());
        return dto;
    }

    public static PositionDto entityToDto(Position position) {
        if (position == null) {
            return null;
        }
        return mapPositionToDto(position);
    }

    public static Position requestToEntity(PositionRequest request) {
        if (request == null) {
            return null;
        }
        Position position = new Position();
        position.setId(request.getId());
        position.setName(request.getName());
        return position;
    }

    public static Position copy(PositionRequest request, Position position) {
        position.setName(request.getName());
        return position;
    }
}
