package com.cardealership.inventoryservice.InventoryManagementSubDomain.DataMapperLayer;

import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.VehicleRequestModel;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory.InventoryIdentifier;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.Price;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.Vehicle;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.VehicleIdentifier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VehicleRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(expression = "java(vehicleIdentifier)", target = "vehicleIdentifier"),
        @Mapping(expression = "java(inventoryIdentifier)", target = "inventoryIdentifier"),
        @Mapping(expression = "java(price)", target = "price"),
    })
    Vehicle requestModelToEntity(VehicleRequestModel vehicleRequestModel, VehicleIdentifier vehicleIdentifier, InventoryIdentifier inventoryIdentifier, Price price);
}
