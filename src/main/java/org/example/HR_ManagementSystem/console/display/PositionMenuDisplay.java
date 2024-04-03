package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.collection.data.PositionDataProcessing;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.console.Clear;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.example.HR_ManagementSystem.console.MenuDisplayed;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;

import java.util.List;
import java.util.Scanner;

public class PositionMenuDisplay extends MenuDisplayed {
    static public boolean running = true;
    PositionDataProcessing positionDataProcessing;
    private final Scanner scanner = new Scanner(System.in);
    private ObjectMapper objectMapper;

    public PositionMenuDisplay() {
        this.positionDataProcessing = new PositionDataProcessing();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
        Clear.clearConsole();
        while (running) {
            System.out.println(ANSI_YELLOW + "Меню должностей!" + ANSI_RESET);
            System.out.println("Выберите действие:");
            System.out.println("1. Создать должность");
            System.out.println("2. Изменить должность");
            System.out.println("3. Удалить должность");
            System.out.println("4. Вывести одну конкретную должность");
            System.out.println("5. Вывести список должностей");
            System.out.println("6. Вывести должности с вложенными сотрудниками");
            System.out.println("7. Назад к основному меню");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (Exception ignore) {
                System.out.println("Неверное значение");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    Clear.clearConsole();
                    createPosition();
                    break;
                case 2:
                    Clear.clearConsole();
                    modifyPosition();
                    break;
                case 3:
                    Clear.clearConsole();
                    deletePosition();
                    break;
                case 4:
                    Clear.clearConsole();
                    printPosition();
                    break;
                case 5:
                    Clear.clearConsole();
                    printAllPositions();
                    break;
                case 6:
                    Clear.clearConsole();
                    printPositionsEmployees();
                    break;
                case 7:
                    Clear.clearConsole();
                    new MenuDisplay().doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
                    break;
            }
        }
    }

    private void modifyPosition() {
        System.out.print("Введите ID должности для изменения:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            System.out.print("Введите новое имя должности:");
            String newName = scanner.nextLine();
            positionDataProcessing.modifyPosition(id, newName);
            System.out.println("Должность успешно изменена.");

        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void createPosition() throws JsonProcessingException {
        scanner.nextLine();
        System.out.print("Введите имя должности:");
        String positionName = scanner.nextLine();
        try {
            PositionDTO positionDTO = positionDataProcessing.createPosition(positionName);
            System.out.println("Должность успешно создана: " + objectMapper.writeValueAsString(positionDTO));
            System.out.println("*******************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void deletePosition() {
        try {
            System.out.print("Введите ID должности для удаления:");
            int id = scanner.nextInt();
            positionDataProcessing.deletePosition(id);
            System.out.println("Должность успешно удалена.");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void printPosition() throws JsonProcessingException {
        try {
            System.out.print("Введите ID должности для вывода:");
            int id = scanner.nextInt();
            PositionDTO positionDTO = positionDataProcessing.printPosition(id);
            System.out.println(objectMapper.writeValueAsString(positionDTO));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void printAllPositions() throws JsonProcessingException {
        try {
            List<Position> positionList = positionDataProcessing.printAllPositions();
            System.out.println(objectMapper.writeValueAsString(positionList));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void printPositionsEmployees() throws JsonProcessingException {
        try {
            List<PositionDTO> positionDTOS = positionDataProcessing.printPositionsEmployees();
            System.out.println(objectMapper.writeValueAsString(positionDTOS));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
