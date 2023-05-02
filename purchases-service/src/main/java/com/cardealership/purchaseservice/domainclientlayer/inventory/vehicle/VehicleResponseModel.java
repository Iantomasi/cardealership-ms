package com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

//@Value
//@Builder
@Data
@AllArgsConstructor
public class VehicleResponseModel extends RepresentationModel<VehicleResponseModel> {

    final String vehicleId;
    final String inventoryId;
    final Status status;
    final UsageType usageType;
    final Integer year ;
    final String manufacturer;
    final String make;
    final String model;
    final String bodyClass;
    final List<Option> options;
    final Price price;

}
