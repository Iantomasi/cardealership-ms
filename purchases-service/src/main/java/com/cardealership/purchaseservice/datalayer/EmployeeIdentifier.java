package com.cardealership.purchaseservice.datalayer;

import org.springframework.data.mongodb.core.index.Indexed;

public class EmployeeIdentifier {
    private String employeeId;

    public EmployeeIdentifier(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
