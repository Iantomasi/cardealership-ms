package com.cardealership.clientservice.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;


@Embeddable
public class ClientIdentifier {
    private String clientId;

    ClientIdentifier() {
        this.clientId = UUID.randomUUID().toString();
    }

    public String getClientId() {
        return this.clientId;
    }

}
