package org.example.HR_ManagementSystem.controllers.controllerView;

import ch.qos.logback.core.model.Model;
import org.example.HR_ManagementSystem.model.request.EmployeeRequest;
import org.example.HR_ManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeViewController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ModelAndView showEmployees(Model model) {
        List<EmployeeRequest> employees = employeeService.getAllEmployees();
        ModelAndView modelAndView = new ModelAndView("employees");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute EmployeeRequest employeeRequest, RedirectAttributes redirectAttributes) {
        employeeService.create(employeeRequest);
        redirectAttributes.addFlashAttribute("message", "Сотрудник успешно добавлен.");
        return "redirect:/employees";
    }

    @PostMapping("/employees/update")
    public String updateEmployee(@ModelAttribute EmployeeRequest employeeRequest, RedirectAttributes redirectAttributes) {
        employeeService.update(employeeRequest.getId(), employeeRequest);
        redirectAttributes.addFlashAttribute("message", "Данные сотрудника обновлены.");
        return "redirect:/employees";
    }

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Сотрудник удален.");
        return "redirect:/employees";
    }
}

