package com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
@Embeddable
public class VehicleIdentifier {

    @Column(unique = true)
    private String vin;

    VehicleIdentifier() {

    }
    public VehicleIdentifier(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }
}