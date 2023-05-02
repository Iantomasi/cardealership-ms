package com.cardealership.clientservice.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    @Embedded
    private ClientIdentifier clientIdentifier;

    private String firstName;
    private String lastName;
    private String emailAddress;

    @Embedded
    private Address address;


    public Client() {
        this.clientIdentifier = new ClientIdentifier();
    }

    public Client(String firstName, String lastName, String emailAddress, String streetAddress, String city, String province, String postalCode, String country) {
        this.clientIdentifier = new ClientIdentifier();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = new Address(streetAddress, city, province, postalCode, country);
    }
}
