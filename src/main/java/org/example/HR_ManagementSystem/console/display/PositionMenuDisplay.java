package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.console.Clear;
import org.example.HR_ManagementSystem.console.MenuDisplay;
import org.example.HR_ManagementSystem.console.MenuDisplayed;
import org.example.HR_ManagementSystem.dto.PositionDto;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;
import org.example.HR_ManagementSystem.model.request.PositionRequest;
import org.example.HR_ManagementSystem.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class PositionMenuDisplay extends MenuDisplayed {

    static public boolean running = true;
    PositionService positionService;
    MenuDisplay menuDisplay;
    private final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper;

    @Autowired
    public PositionMenuDisplay(PositionService positionService, @Lazy MenuDisplay menuDisplay) {
        this.positionService = positionService;
        this.menuDisplay = menuDisplay;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    public void doDisplay() throws JsonProcessingException {
        Clear.clearConsole();
        while (running) {
            System.out.println("   " + ANSI_YELLOW + "Меню должностей!" + ANSI_RESET);
            System.out.println("*" + "  " + ANSI_BLUE + "\u001B[4m" + "\u001B[1m" + "Выберите действие:" + ANSI_RESET);
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
                    create();
                    break;
                case 2:
                    Clear.clearConsole();
                    update();
                    break;
                case 3:
                    Clear.clearConsole();
                    delete();
                    break;
                case 4:
                    Clear.clearConsole();
                    findById();
                    break;
                case 5:
                    Clear.clearConsole();
                    read();
                    break;
                case 6:
                    Clear.clearConsole();
                    getPositionsWithEmployees();
                    break;
                case 7:
                    Clear.clearConsole();
                    menuDisplay.doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
                    break;
            }
        }
    }

    private void update() {
        try {
            PositionRequest request = new PositionRequest();
            System.out.print("Введите ID должности для изменения:");
            Long id = scanner.nextLong();
            request.setId(id);
            scanner.nextLine();

            System.out.print("Введите новое имя должности:");
            request.setName(scanner.nextLine());

            positionService.update(request);
            System.out.println("Должность успешно изменена.");

        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
            running = false;
        }
    }

    private void create() throws JsonProcessingException {
        scanner.nextLine();
        System.out.print("Введите имя должности:");
        String positionName = scanner.nextLine();
        PositionRequest positionRequest = new PositionRequest();
        positionRequest.setName(positionName);
        try {
            PositionDto position = positionService.create(positionRequest);
            System.out.println("Должность успешно создана: " + objectMapper.writeValueAsString(position));
            System.out.println("*******************************************************");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void delete() {
        try {
            System.out.print("Введите ID должности для удаления:");
            Long id = scanner.nextLong();
            positionService.delete(id);
            System.out.println("Должность успешно удалена.");
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void findById() throws JsonProcessingException {
        try {
            System.out.print("Введите ID должности для вывода:");
            Long id = scanner.nextLong();
            PositionDto position = positionService.findById(id);
            System.out.println(objectMapper.writeValueAsString(position));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void read() throws JsonProcessingException {
        try {
            List<PositionDto> positionList = positionService.read();
            System.out.println(objectMapper.writeValueAsString(positionList));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }

    private void getPositionsWithEmployees() throws JsonProcessingException {
        try {
            List<PositionDto> positionDTOS = positionService.getPositionsWithEmployees();
            System.out.println(objectMapper.writeValueAsString(positionDTOS));
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
