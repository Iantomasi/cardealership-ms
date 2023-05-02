package com.cardealership.purchaseservice.datalayer;

import org.springframework.data.mongodb.core.index.Indexed;

public class VehicleIdentifier {
    //@Indexed(unique = true)
    private String vin;

    public VehicleIdentifier(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }
}
