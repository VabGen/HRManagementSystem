package org.example.HR_ManagementSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.service.EmployeeManagementService;
import org.example.HR_ManagementSystem.service.Menu;
import org.example.HR_ManagementSystem.service.PositionManagementService;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Menu menu = new Menu();
        if (Arrays.asList(args).contains("1")){
            System.out.println("*****************************");
        };

//  ********************************************************************************************************************
        EmployeeManagementService employeeManagementServiceInstance = EmployeeManagementService.getInstance();
        employeeManagementServiceInstance.createEmployee("Ivanov", "Ivan", "Ivanovich", 1);
        employeeManagementServiceInstance.createEmployee("Petrov", "Maksim", "Vicnorovich", 2);
        employeeManagementServiceInstance.createEmployee("Sidorov", "Dmitriy", "Aleksandrovich", 3);
        employeeManagementServiceInstance.createEmployee("Voronin", "Denis", "Anatolevich", 1);

        PositionManagementService positionManagementService = PositionManagementService.getInstance();
        positionManagementService.createPosition("engineer");
        positionManagementService.createPosition("accountant");
        positionManagementService.createPosition("programmer");
//  ********************************************************************************************************************

        menu.doDisplay();
    }
}