package com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


//@Value
//@Builder
@Data
@AllArgsConstructor
public class VehicleInventoryResponseModel extends RepresentationModel<VehicleInventoryResponseModel> {
    //inventory details response (get inventory by id)

    String inventoryId;
    String type;
    List<VehicleResponseModel> availableVehicles;
}
