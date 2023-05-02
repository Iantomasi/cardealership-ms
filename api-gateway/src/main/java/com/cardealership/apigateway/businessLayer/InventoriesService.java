package com.cardealership.apigateway.businessLayer;

import com.cardealership.apigateway.presentationLayer.*;

public interface InventoriesService {
    VehicleInventoryResponseModel getInventoryAggregate(String inventoryId);
    InventoryResponseModel[] getAllInventoriesAggregate();
    InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);
    VehicleResponseModel addVehicleToInventory(String inventoryId, VehicleRequestModel newVehicle);
    void updateVehicleInInventoryByVehicleId(VehicleRequestModel vehicleRequestModel, String inventoryId, String vehicleId);
    void updateInventoryAggregate(String inventoryId, InventoryRequestModel inventoryRequestModel);
    void deleteVehicleFromInventory(String inventoryId, String vehicleId);
    void deleteInventoryAggregate(String inventoryId);
}
