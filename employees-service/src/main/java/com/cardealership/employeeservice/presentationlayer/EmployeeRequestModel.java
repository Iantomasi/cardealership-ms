package com.cardealership.employeeservice.presentationlayer;

import com.cardealership.employeeservice.datalayer.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeRequestModel {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Double salary;
    private Double commissionRate;
    private Department department;
    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;
}
