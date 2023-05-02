package com.cardealership.inventoryservice.InventoryManagementSubDomain.DataMapperLayer;

import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.InventoryController;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.VehicleResponseModel;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.Vehicle;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Mapper(componentModel ="spring")
public interface VehicleResponseMapper {

    @Mapping(expression = "java(vehicle.getVehicleIdentifier().getVin())", target = "vehicleId")
    @Mapping(expression = "java(vehicle.getInventoryIdentifier().getInventoryId())", target = "inventoryId")
    VehicleResponseModel entityToResponseModel(Vehicle vehicle);

    List<VehicleResponseModel> entityListToResponseModelList(List<Vehicle> vehicle);


    @AfterMapping
    default void addLinks(@MappingTarget VehicleResponseModel model, Vehicle vehicle) {

        //self Link
        Link selfLink = linkTo(methodOn(InventoryController.class)
                .getVehicleInInventory(model.getInventoryId(), model.getVehicleId()))
                .withSelfRel();
        model.add(selfLink);

        //Inventory by id
        Link inventoryLink = linkTo(methodOn(InventoryController.class)
                .getInventoryDetails(model.getInventoryId()))
                .withRel("inventories");
        model.add(inventoryLink);

        Link vehiclesLink = linkTo(methodOn(InventoryController.class)
                .getVehiclesInInventory(model.getInventoryId(),null))
                .withRel("allVehiclesInInventory");

        model.add(vehiclesLink);
    }
}
