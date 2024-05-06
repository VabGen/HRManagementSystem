package org.example.HR_ManagementSystem.console.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.HR_ManagementSystem.exception.ExceptionHandler;
import org.example.HR_ManagementSystem.model.dto.EmployeeDTO;
import org.example.HR_ManagementSystem.model.filter.EmployeeFilter;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.service.EmployeeService;
import org.example.HR_ManagementSystem.source.data.EmployeeServiceDao;
import org.example.HR_ManagementSystem.source.data.impl.EmployeeServiceDaoImpl;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;

@Component
public class EmployeesByFilterDisplay {

    private final EmployeeServiceDao employeeServiceDao;
    private final EmployeeService employeeService;
    Scanner scanner = new Scanner(System.in);
    ObjectMapper objectMapper;

    @Autowired
    public EmployeesByFilterDisplay(EmployeeServiceDao employeeServiceDao, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeServiceDao = employeeServiceDao;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public void searchAll() throws JsonProcessingException {
        System.out.println("*" + "  " + "\u001B[34m" + "\u001B[4m" + "\u001B[1m" + "Введите данные для поиска сотрудников:" + "\u001B[0m");
        System.out.println("Введите Ф.И.О для поиска сотрудников:");
        String fio = scanner.nextLine();
        if (fio.isEmpty()) {
            fio = null;
        }

        System.out.println("Формат даты: \"yyyy-MM-ddTHH:mm:ssZ\"");
        System.out.println("Введите дату с:");
        String startDateInput = scanner.nextLine();
        Instant startDate = null;
        ZonedDateTime startDateTime = null;

        if (!startDateInput.isEmpty()) {
            startDate = Instant.parse(startDateInput);
            startDateTime = ZonedDateTime.ofInstant(startDate, ZoneId.systemDefault());
        }

        System.out.println("Введите дату по:");
        String endDateInput = scanner.nextLine();
        Instant endDate = null;
        ZonedDateTime endDateTime = null;

        if (!endDateInput.isEmpty()) {
            endDate = Instant.parse(endDateInput);
            endDateTime = ZonedDateTime.ofInstant(endDate, ZoneId.systemDefault());
        }

        System.out.println("Введите positionName для поиска сотрудников:");
        String positionName = scanner.nextLine();
        if (positionName.isEmpty()) {
            positionName = null;
        }

        System.out.println("Введите terminates для поиска сотрудников:");
        Boolean terminates = null;
        String terminatesInput = scanner.nextLine();
        if (!terminatesInput.isEmpty()) {
            terminates = Boolean.valueOf(terminatesInput);
        }

        EmployeeFilter filter = new EmployeeFilter();
        filter.setFio(fio);
        filter.setStartDate(startDateTime);
        filter.setEndDate(endDateTime);
        filter.setPositionName(positionName);
        filter.setTerminates(terminates);

        try {
            List<EmployeeRequest> employeeList = employeeService.findAll(filter);
            for (EmployeeRequest employee : employeeList) {
                System.out.println(objectMapper.writeValueAsString(employee));
            }
        } catch (RuntimeException e) {
            ExceptionHandler.handleException(e);
        }
    }
}
