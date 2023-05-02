package com.cardealership.purchaseservice.domainclientlayer.inventory;

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
