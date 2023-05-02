package com.cardealership.inventoryservice.InventoryManagementSubDomain.DataMapperLayer;

import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.InventoryController;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.VehicleInventoryResponseModel;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.VehicleResponseModel;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory.Inventory;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface VehicleInventoryResponseMapper {

    @Mapping(expression = "java(inventory.getInventoryIdentifier().getInventoryId())", target = "inventoryId")
    @Mapping(expression = "java(vehicles)", target = "availableVehicles")
    VehicleInventoryResponseModel entitiesToResponseModel(Inventory inventory, List<VehicleResponseModel> vehicles);

    @AfterMapping
    default void addLinks(@MappingTarget VehicleInventoryResponseModel model, Inventory inventory){
        //self Link
        Link selfLink = linkTo(methodOn(InventoryController.class)
                .getInventoryDetails(model.getInventoryId()))
                .withSelfRel();
        model.add(selfLink);
    }

}
