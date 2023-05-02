package com.cardealership.purchaseservice.datalayer;

import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

public class PurchaseOrderIdentifier {
    @Indexed(unique = true)
    private String purchaseOrderId;

    public PurchaseOrderIdentifier() {
        this.purchaseOrderId = UUID.randomUUID().toString();
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }
}
