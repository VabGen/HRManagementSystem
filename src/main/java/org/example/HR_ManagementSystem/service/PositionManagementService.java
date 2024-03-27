package org.example.HR_ManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PositionManagementService {
    private static PositionManagementService positionManagementService;
    private final List<Position> positions;

    private final ObjectMapper objectMapper;

    PositionManagementService() {
        positions = new ArrayList<>();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static PositionManagementService getInstance() {
        if (positionManagementService == null) {
            positionManagementService = new PositionManagementService();
        }
        return positionManagementService;
    }

    public void createPosition(String name) throws JsonProcessingException {
        for (Position position : positions) {
            if (position.getName().equals(name)) {
                System.out.println("Должность с таким именем уже существует.");
                return;
            }
        }
        Position newPosition = new Position(name);
        positions.add(newPosition);
        System.out.println("Должность успешно создана: " + objectMapper.writeValueAsString(newPosition));
    }

    public void modifyPosition(int id, String newName) {
        Position position = findPositionById(id);
        if (position != null) {
            position.setName(newName);
            System.out.println("Должность успешно изменена.");
        } else {
            System.out.println("Должность не найдена.");
        }
    }

    public void deletePosition(int id) {
        Position position = findPositionById(id);
        if (position != null) {
            positions.remove(position);
            System.out.println("Должность успешно удалена.");
        } else {
            System.out.println("Должность не найдена.");
        }
    }

    public void printPosition(int id) throws JsonProcessingException {
        Position position = findPositionById(id);
        if (position != null) {
            EmployeeManagementService employeeManagementService = EmployeeManagementService.getInstance();
            List<Employee> list = employeeManagementService.findAllByPositionId(id);
            Position result = new Position(position.getId(), position.getName());
            result.setEmployees(list);
            System.out.println(objectMapper.writeValueAsString(result));
        } else {
            System.out.println("Должность не найдена.");
        }
    }

    public void printAllPositions() throws JsonProcessingException {
        if (positions.isEmpty()) {
            System.out.println("Список должностей пуст.");
            new ShowMenuPosition().createPosition();
        }
        System.out.println(objectMapper.writeValueAsString(positions));
    }


    public void printPositionsEmployees() throws JsonProcessingException {
        EmployeeManagementService employeeManagementService = EmployeeManagementService.getInstance();
        if (positions.isEmpty()) {
            System.out.println("Список должностей пуст.");
            return;
        }

        List<Position> positionList = new ArrayList<>();

        for (Position position : positions) {
            Position newPosition = new Position(position.getId(), position.getName());
            List<Employee> list = employeeManagementService.findAllByPositionId(position.getId());
            newPosition.setEmployees(list);
            positionList.add(newPosition);
        }
        positionList.sort(Comparator.comparing(Position::getName));
        System.out.println(objectMapper.writeValueAsString(positionList));
    }

    public Position findPositionById(int id) {
        for (Position position : positions) {
            if (position.getId() == id) {
                return position;
            }
        }
        return null;
    }
}