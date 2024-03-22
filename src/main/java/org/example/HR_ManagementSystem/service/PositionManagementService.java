package org.example.HR_ManagementSystem.service;

import org.example.HR_ManagementSystem.entity.Position;
import org.example.HR_ManagementSystem.entity.Employee;

import java.util.ArrayList;
import java.util.List;

public class PositionManagementService {
    private final List<Position> positions;

    public PositionManagementService() {
        positions = new ArrayList<>();
    }

    public void createPosition(String name) {
        for (Position position : positions) {
            if (position.getName().equals(name)) {
                System.out.println("Должность с таким именем уже существует.");
                return;
            }
        }
        Position newPosition = new Position(name);
        positions.add(newPosition);
        System.out.println("Должность успешно создана: " + newPosition);
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

    public void printPosition(int id) {
        Position position = findPositionById(id);
        if (position != null) {
            System.out.println(position);
        } else {
            System.out.println("Должность не найдена.");
        }
    }

    public void printAllPositions() {
        if (positions.isEmpty()) {
            System.out.println("Список должностей пуст.");
            return;
        }

        for (Position position : positions) {
            System.out.println(position);
        }
    }

    public void printEmployeesByFilter(String filter) {
        if (positions.isEmpty()) {
            System.out.println("Список должностей пуст.");
            return;
        }

        for (Position position : positions) {
            if (position.getName().contains(filter)) {
                System.out.println(position);
            }
        }
    }

    private Position findPositionById(int id) {
        for (Position position : positions) {
            if (position.getId() == id) {
                return position;
            }
        }
        return null;
    }
}