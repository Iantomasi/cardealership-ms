package com.cardealership.purchaseservice.datalayer;

import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

public class InventoryIdentifier {
    private String inventoryId;

    public InventoryIdentifier(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryId() {
        return inventoryId;
    }
}
