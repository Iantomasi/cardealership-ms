package com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventories")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private id

    @Embedded
    private InventoryIdentifier inventoryIdentifier; //public id

    private String type;

    //@Where(clause = "status = Status.AVAILABLE")
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryIdentifier")
    //@JoinColumn(name="inventory_id")
    //private List<Vehicle> availableVehicles = new ArrayList<>();


    public Inventory() {
        this.inventoryIdentifier = new InventoryIdentifier();
    }

    public Inventory(String type) {
        this.inventoryIdentifier = new InventoryIdentifier();
        this.type = type;
    }
}

