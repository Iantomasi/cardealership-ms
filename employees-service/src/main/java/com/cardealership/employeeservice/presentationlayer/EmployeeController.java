package com.cardealership.employeeservice.presentationlayer;


import com.cardealership.employeeservice.businesslayer.EmployeeService;
import com.cardealership.employeeservice.datalayer.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployeeByEmployeeId(@PathVariable UUID employeeId) {
        return employeeService.getEmployeeByEmployeeId(employeeId);
    }

    @PostMapping()
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        return employeeService.addEmployee(newEmployee);
    }

    @PutMapping("/{employeeId}")
    public Employee updateEmployee(@RequestBody Employee updatedEmployee, @PathVariable UUID employeeId) {
        return employeeService.updateEmployee(updatedEmployee, employeeId);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.removeEmployee(employeeId);
    }
}