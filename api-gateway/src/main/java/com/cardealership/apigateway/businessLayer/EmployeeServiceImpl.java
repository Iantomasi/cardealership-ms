package com.cardealership.apigateway.businessLayer;

import com.cardealership.apigateway.domainClientLayer.EmployeeServiceClient;
import com.cardealership.apigateway.presentationLayer.EmployeeResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeServiceClient employeeServiceClient;

    public EmployeeServiceImpl(EmployeeServiceClient employeeServiceClient) {
        this.employeeServiceClient = employeeServiceClient;
    }

    @Override
    public EmployeeResponseModel getEmployeeAggregate(String employeeId) {
        return employeeServiceClient.getEmployee(employeeId);
    }
}
