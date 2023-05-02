package com.cardealership.employeeservice.businesslayer;

import com.cardealership.employeeservice.datalayer.Employee;
import com.cardealership.employeeservice.datalayer.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeByEmployeeId(UUID employeeId) {
        return employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeId.toString());
    }

    @Override
    public Employee addEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee, UUID employeeId) {

        Employee existingEmployee = employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeId.toString());

        if (existingEmployee == null) {
            return null; //later update with exception
        }

        updatedEmployee.setId(existingEmployee.getId());
        updatedEmployee.setEmployeeIdentifier(existingEmployee.getEmployeeIdentifier());

        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void removeEmployee(UUID employeeId) {

        Employee existingEmployee = employeeRepository.findByEmployeeIdentifier_EmployeeId(employeeId.toString());

        if (existingEmployee == null) {
            return; //later update with exception
        }

        employeeRepository.delete(existingEmployee);
    }
}