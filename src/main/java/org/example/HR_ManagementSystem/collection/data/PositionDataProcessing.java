package org.example.HR_ManagementSystem.collection.data;

import org.example.HR_ManagementSystem.collection.entity.Employee;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.collection.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.collection.service.PositionManagementService;
import org.example.HR_ManagementSystem.model.request.PositionRequest;
import org.example.HR_ManagementSystem.model.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.model.dto.PositionDTO;
import org.example.HR_ManagementSystem.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PositionDataProcessing {
    private final PositionManagementService positionManagementService;

    public PositionDataProcessing() {
        this.positionManagementService = PositionManagementService.getInstance();
    }

    public PositionDTO create(String positionName) {

        if (positionManagementService.isPositionExists(positionName)) {
            throw new BadRequestException("Position name cannot be empty");
        }
        return positionManagementService.createPosition(positionName);
    }

    public PositionDTO modify(PositionRequest request) {
        Optional<Position> optionalPosition = positionManagementService.findPositionById(request.getId());

        if (optionalPosition.isEmpty()) {
            throw new BadRequestException("Position not found");
        }
        return positionManagementService.modifyPosition(optionalPosition.get(), request.getName());
    }

    public void delete(int id) {
        Optional<Position> positionById = positionManagementService.findPositionById(id);

        if (positionById.isEmpty()) {
            throw new BadRequestException("Position not found");
        }
        positionManagementService.deletePosition(positionById.get().getId());
    }

    public PositionDTO find(int id) {
        Optional<Position> position = positionManagementService.findPositionById(id);

        if (position.isEmpty()) {
            throw new BadRequestException("Position not found");
        } else {
            PositionDTO positionDTO = new PositionDTO(position.get());
            EmployeeManagementService employeeManagementService = EmployeeManagementService.getInstance();
            List<Employee> employees = employeeManagementService.findAllByPositionId(positionDTO.getId()).stream().toList();
            positionDTO.setEmployees(employeeListToDto(employees));
            return positionDTO;
        }
    }

    public List<Position> printAll() {
        List<Position> positionList = positionManagementService.printAllPositions();

        if (positionList.isEmpty()) {
            throw new BadRequestException("Position not found");
        }
        return positionList;
    }

    public List<PositionDTO> printPositionsEmployees() {
        List<Position> positionList = positionManagementService.printPositionsEmployees();

        if (positionList == null || positionList.isEmpty()) {
            throw new BadRequestException("Position not found");
        } else {
            List<PositionDTO> dtos = positionListToDto(positionList);
            return dtos;
        }
    }

    public List<EmployeeDTO> employeeListToDto(List<Employee> employees) {
        List<EmployeeDTO> dtos = new ArrayList<>();
        if (employees != null){
            employees.forEach(e -> dtos.add(new EmployeeDTO(e)));
        }
        return dtos;
    }

    public List<PositionDTO> positionListToDto(List<Position> positions) {
        List<PositionDTO> dtos = new ArrayList<>();
        for (Position position : positions) {
            PositionDTO dto = new PositionDTO(position);
            dto.setEmployees(employeeListToDto(position.getEmployees()));
            dtos.add(dto);
        }
        return dtos;
    }
}