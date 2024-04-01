package org.example.HR_ManagementSystem.console;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.example.HR_ManagementSystem.entity.Employee;
import org.example.HR_ManagementSystem.entity.Position;
import org.example.HR_ManagementSystem.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.service.PositionManagementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PositionDataProcessing {
    private final PositionManagementService positionManagementService;
    private final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper;

    public PositionDataProcessing() {
        this.positionManagementService = PositionManagementService.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public void createPosition() throws JsonProcessingException {
        System.out.print("Введите имя должности:");
        String positionName = scanner.nextLine();

        if (positionManagementService.isPositionExists(positionName)) {
            System.out.println("Должность с таким именем уже существует.");
            return;
        }
        Position position = positionManagementService.createPosition(positionName);
        PositionDTO positionDTO = new PositionDTO(position);
        System.out.println("Должность успешно создана: " + objectMapper.writeValueAsString(positionDTO));
        System.out.println("*******************************************************");
    }

    public void modifyPosition() {
        System.out.print("Введите ID должности для изменения:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Position> optionalPosition = positionManagementService.findPositionById(id);

        if (optionalPosition.isPresent()) {
            System.out.print("Введите новое имя должности:");
            String newName = scanner.nextLine();
            positionManagementService.modifyPosition(optionalPosition.get(), newName);
            System.out.println("Должность успешно изменена.");
        } else {
            System.out.println("Должность не найдена.");
        }
    }

    public void deletePosition() {
        System.out.print("Введите ID должности для удаления:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Position> positionById = positionManagementService.findPositionById(id);

        if (positionById.isPresent()) {
            positionManagementService.deletePosition(positionById.get().getId());
            System.out.println("Должность успешно удалена.");
        } else {
            System.out.println("Должность не найдена.");
        }
    }

    public void printPosition() throws JsonProcessingException {
        System.out.print("Введите ID должности для вывода:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Position> position = positionManagementService.findPositionById(id);

        if (position.isPresent()) {
            PositionDTO positionDTO = new PositionDTO(position.get());
            EmployeeManagementService employeeManagementService = EmployeeManagementService.getInstance();
            List<Employee> employees = employeeManagementService.findAllByPositionId(positionDTO.getId()).stream().toList();
            positionDTO.setEmployees(employeeListToDto(employees));
            System.out.println(objectMapper.writeValueAsString(positionDTO));
        } else {
            System.out.println("Должность с указанным ID не найдена.");
        }
    }

    public void printAllPositions() throws JsonProcessingException {
        List<Position> positionList = positionManagementService.printAllPositions();

        if (positionList.isEmpty()) {
            System.out.println("Список должностей пуст.");
        }
        System.out.println(objectMapper.writeValueAsString(positionList));
    }

    public void printPositionsEmployees() throws JsonProcessingException {
        List<Position> positionList = positionManagementService.printPositionsEmployees();

        if (positionList != null && !positionList.isEmpty()) {
            List<PositionDTO> dtos = positionListToDto(positionList);
            System.out.println(objectMapper.writeValueAsString(dtos));
        } else {
            System.out.println("Список должностей сотрудников пуст или не найден.");
        }
    }

    private List<EmployeeDTO> employeeListToDto(List<Employee> employees) {
        List<EmployeeDTO> dtos = new ArrayList<>();
        employees.forEach(e -> dtos.add(new EmployeeDTO(e)));
        return dtos;
    }

    private List<PositionDTO> positionListToDto(List<Position> positions) {
        List<PositionDTO> dtos = new ArrayList<>();
        for (Position position : positions) {
            PositionDTO dto = new PositionDTO(position);
            dto.setEmployees(employeeListToDto(position.getEmployees()));
            dtos.add(dto);
        }
        return dtos;
    }
}