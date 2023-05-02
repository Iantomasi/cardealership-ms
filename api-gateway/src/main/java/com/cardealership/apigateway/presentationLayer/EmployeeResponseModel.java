package com.cardealership.apigateway.presentationLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmployeeResponseModel {
    private String employeeId;
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
