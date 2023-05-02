package com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllByInventoryIdentifier_InventoryId(String inventoryId);
    List<Vehicle> findAllByInventoryIdentifier_InventoryIdAndStatusEquals(String inventoryId, Status status);
    List<Vehicle> findAllByInventoryIdentifier_InventoryIdAndUsageTypeEquals(String inventoryId, UsageType usageType);
    List<Vehicle> findAllByInventoryIdentifier_InventoryIdAndStatusEqualsAndUsageTypeEquals(String inventoryId, Status status, UsageType usageType);
    Vehicle findByInventoryIdentifier_InventoryIdAndVehicleIdentifier_Vin(String inventoryId, String vin);
    Vehicle findByVehicleIdentifier_Vin(String vin);

      //      List<Vehicle> findAllByInventory_InventoryIdentifier_InventoryId(String inventoryId);
      //      Vehicle findByInventory_InventoryIdentifier_InventoryIdAndVehicleIdentifier_Vin(String inventoryId, String vin);
      //      List<Vehicle> findAllByInventory_InventoryIdentifier_InventoryIdAndStatusEqualsAndUsageTypeEquals(String InventoryId, Status status, UsageType usageType);
      //      List<Vehicle> findAllByInventory_InventoryIdentifier_InventoryIdAndUsageTypeEquals(String inventoryId, UsageType usageType);
      //      List<Vehicle> findAllByInventory_InventoryIdentifier_InventoryIdAndStatusEquals(String inventoryId, Status status);
}
