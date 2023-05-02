package com.cardealership.apigateway.businessLayer;


import com.cardealership.apigateway.presentationLayer.EmployeeResponseModel;

public interface EmployeeService {
    EmployeeResponseModel getEmployeeAggregate(String employeeId);
}
