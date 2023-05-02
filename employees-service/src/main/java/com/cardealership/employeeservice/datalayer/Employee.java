package com.cardealership.employeeservice.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private identifier

    @Embedded
    private EmployeeIdentifier employeeIdentifier; //public identifier

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;

    private Double salary;
    private Double commissionRate;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Embedded
    private Address address;


    public Employee() {
        this.employeeIdentifier = new EmployeeIdentifier();
    }

    public Employee(String firstName, String lastName, Address address, String emailAddress) {
        this.employeeIdentifier = new EmployeeIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emailAddress = emailAddress;
    }
}