package com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer;

public class InventoryRequestModel {

    String type;

    public InventoryRequestModel() {
    }

    public InventoryRequestModel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
