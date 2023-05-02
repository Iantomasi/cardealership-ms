package com.cardealership.employeeservice.businesslayer;

import com.cardealership.employeeservice.datalayer.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    List<Employee> getEmployees();
    Employee getEmployeeByEmployeeId(UUID employeeId);
    Employee addEmployee(Employee newEmployee);
    Employee updateEmployee(Employee updatedEmployee, UUID employeeId);
    void removeEmployee(UUID employeeId);
}