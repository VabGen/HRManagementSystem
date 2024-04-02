package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.console.PositionDataProcessing;

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
                    positionDataProcessing.createPosition();
                    break;
                case 2:
                    positionDataProcessing.modifyPosition();
                    break;
                case 3:
                    positionDataProcessing.deletePosition();
                    break;
                case 4:
                    positionDataProcessing.printPosition();
                    break;
                case 5:
                    positionDataProcessing.printAllPositions();
                    break;
                case 6:
                    positionDataProcessing.printPositionsEmployees();
                    break;
                case 7:
                    new MenuDisplay().doDisplay();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Введите корректное число от 1 до 7 \n" + ANSI_YELLOW + "Попробуйте еще раз." + ANSI_RESET);
                    System.out.println("**********************************************************************************************************");
                    break;
            }
        }
    }
}
