package com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory;

import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public class InventoryIdentifier {

    private String inventoryId;

    InventoryIdentifier() {
        this.inventoryId = UUID.randomUUID().toString();
    }

    public String getInventoryId() {
        return inventoryId;
    }
}
