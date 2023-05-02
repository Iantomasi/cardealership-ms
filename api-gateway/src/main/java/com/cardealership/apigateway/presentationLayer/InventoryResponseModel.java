package com.cardealership.apigateway.presentationLayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

//@Value
//@Builder
@Data
@AllArgsConstructor
public class InventoryResponseModel extends RepresentationModel<InventoryResponseModel> {

    String inventoryId; //public id
    String type;
}
