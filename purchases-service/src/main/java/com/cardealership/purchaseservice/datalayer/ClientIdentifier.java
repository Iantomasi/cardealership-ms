package com.cardealership.purchaseservice.datalayer;

import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

public class ClientIdentifier {
    private String clientId;

    public ClientIdentifier(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
