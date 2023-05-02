package com.cardealership.apigateway.businessLayer;

import com.cardealership.apigateway.domainClientLayer.InventoryServiceClient;
import com.cardealership.apigateway.presentationLayer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class inventoriesServiceImpl implements InventoriesService{

    private InventoryServiceClient inventoryServiceClient;
    public inventoriesServiceImpl(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }
    @Override
    public VehicleInventoryResponseModel getInventoryAggregate(String inventoryId) {
        log.debug("2. Received in API-Gateway Inventory Service IMPL getInventoryAggregate with inventoryId: "+inventoryId);
        return inventoryServiceClient.getInventoryAggregate(inventoryId);
    }

    @Override
    public InventoryResponseModel[] getAllInventoriesAggregate() {
        return inventoryServiceClient.getAllInventoriesAggregate();
    }

    @Override
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel) {
        log.debug("3. Received in api gateway inventory service impl addInventory with type " + inventoryRequestModel.getType());
        return inventoryServiceClient.addInventoryAggregate(inventoryRequestModel);
    }

    @Override
    public void updateVehicleInInventoryByVehicleId(VehicleRequestModel vehicleRequestModel, String inventoryId, String vehicleId) {
        inventoryServiceClient.modifyVehicleInInventory(inventoryId, vehicleId, vehicleRequestModel);
    }
    @Override
    public VehicleResponseModel addVehicleToInventory(String inventoryId, VehicleRequestModel newVehicle) {
        return inventoryServiceClient.addVehicleToInventory(inventoryId, newVehicle);
    }

    @Override
    public void updateInventoryAggregate(String inventoryId, InventoryRequestModel inventoryRequestModel) {
        log.debug("3. Received in api gateway inventory service impl updateInventory with type " + inventoryRequestModel.getType());
        inventoryServiceClient.updateInventoryAggregate(inventoryId, inventoryRequestModel);
    }

    @Override
    public void deleteVehicleFromInventory(String inventoryId, String vehicleId) {
        inventoryServiceClient.deleteVehicleInInventory(inventoryId, vehicleId);
    }
    @Override
    public void deleteInventoryAggregate(String url) {
        inventoryServiceClient.deleteInventory(url);
    }

}
