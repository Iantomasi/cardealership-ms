package com.cardealership.inventoryservice.InventoryManagementSubDomain.DataMapperLayer;


import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.InventoryRequestModel;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inventoryIdentifier", ignore = true)
    Inventory requestModelToEntity(InventoryRequestModel requestModel);
}
