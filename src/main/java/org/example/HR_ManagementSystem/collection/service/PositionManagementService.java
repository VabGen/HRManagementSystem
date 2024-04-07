package org.example.HR_ManagementSystem.collection.service;

import org.example.HR_ManagementSystem.collection.entity.Employee;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.dto.PositionDTO;

import java.util.*;

public class PositionManagementService {
    private static PositionManagementService positionManagementService;
    private final Map<Integer, Position> positions;
    private final boolean initData = true;

    PositionManagementService() {
        positions = new HashMap<>();
        if (initData) {
            List<Position> positionList = Arrays.asList(
                    new Position("Software Engineer"),
                    new Position("System Administrator"),
                    new Position("IT Project Manager")
            );
            positionList.forEach(p -> positions.put(p.getId(), p));
        }
    }

    public static PositionManagementService getInstance() {

        if (positionManagementService == null) {
            positionManagementService = new PositionManagementService();
        }
        return positionManagementService;
    }

    public PositionDTO createPosition(String name) {
        Position newPosition = new Position(name);
        positions.put(newPosition.getId(), newPosition);
        return new PositionDTO(newPosition);
    }

    public PositionDTO modifyPosition(Position position, String newName) {
        position.setName(newName);
        return new PositionDTO(position);
    }

    public void deletePosition(int id) {
        positions.remove(id);
    }

    public List<Position> printAllPositions() {
        ArrayList<Position> positionsList = new ArrayList<>(positions.values());
        return positionsList;
    }

    public List<Position> printPositionsEmployees() {
        EmployeeManagementService employeeManagementService = EmployeeManagementService.getInstance();

        if (positions.isEmpty()) return null;

        List<Position> positionList = new ArrayList<>();
        for (Position position : positions.values()) {
            Position newPosition = new Position(position.getId(), position.getName());
            List<Employee> employeeList = employeeManagementService.findAllByPositionId(position.getId());

            if (!employeeList.isEmpty()) {
                List<Employee> filterEmployee = employeeList.stream()
                        .filter(employee -> !employee.isTerminated()).toList();
                newPosition.setEmployees(filterEmployee);
            }
            positionList.add(newPosition);
        }
        positionList.sort(Comparator.comparing(Position::getName));
        return positionList;
    }

    public Optional<Position> findPositionById(int id) {
        return Optional.of(positions.get(id));
    }

    public boolean isPositionExists(String name) {
        return positions.values().stream().anyMatch(position -> position.getName().equals(name));
    }
}
