package com.cardealership.inventoryservice.InventoryManagementSubDomain.BusinessLayer;


import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.*;
import java.util.List;
import java.util.Map;

public interface VehicleInventoryService {
    List<InventoryResponseModel> getInventories( );
    List<VehicleResponseModel> getVehiclesInventoryByField(String inventoryId, Map<String, String> queryParams);
    VehicleInventoryResponseModel getInventoryDetails(String inventoryId);
    InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);
    VehicleResponseModel addVehicleToInventory(VehicleRequestModel vehicleRequestModel, String inventoryId);
    InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, String inventoryId);
    VehicleResponseModel updateVehicleInInventory(VehicleRequestModel vehicleRequestModel, String inventoryId, String vehicleId);
    VehicleResponseModel getVehiclesInInventoryByVehicleId(String inventoryId, String vehicleId);
    void removeVehicleFromInventory(String inventoryId, String vehicleId);
    void deleteInventoryAndContent(String inventoryId);
}
