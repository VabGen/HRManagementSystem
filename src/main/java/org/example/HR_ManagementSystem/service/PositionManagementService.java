package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;

import java.util.*;

public class PositionManagementService {
    private static PositionManagementService positionManagementService;
    private final Map<Integer, Position> positions;
    private final ObjectMapper objectMapper;
    private final boolean initData = true;

    PositionManagementService() {
        positions = new HashMap<>();
        if (initData) {
            List<Position> positionList = Arrays.asList(
                    new Position("1"),
                    new Position("2"),
                    new Position("3")
            );
            positionList.forEach(p -> positions.put(p.getId(), p));
        }
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static PositionManagementService getInstance() {

        if (positionManagementService == null) {
            positionManagementService = new PositionManagementService();
        }
        return positionManagementService;
    }

    public Position createPosition(String name) {
        Position newPosition = new Position(name);
        positions.put(newPosition.getId(), newPosition);
        return newPosition;
    }

    public Position modifyPosition(Position position, String newName) {
        position.setName(newName);
        return position;
    }

    public void deletePosition(int id) {
        positions.remove(id);
    }

    public List<Position> printAllPositions() {
        return new ArrayList<>(positions.values());
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
