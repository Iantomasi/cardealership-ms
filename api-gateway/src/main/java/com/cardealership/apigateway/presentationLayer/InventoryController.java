package com.cardealership.apigateway.presentationLayer;

import com.cardealership.apigateway.Utils.Exceptions.InvalidInputException;
import com.cardealership.apigateway.businessLayer.InventoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {

    private final Integer UUID_SIZE=36;
    private InventoriesService inventoriesService;

    public InventoryController(InventoriesService inventoriesService) {
        this.inventoriesService = inventoriesService;
    }

    @GetMapping(
            value="/{inventoryId}",
            produces="application/json"
    )
    ResponseEntity<VehicleInventoryResponseModel> getInventoryVehicleAggregate(@PathVariable String inventoryId){
        if(inventoryId.length()!=UUID_SIZE){
            throw new InvalidInputException("Inventory id is invalid: " + inventoryId);
        }

        log.debug("1. Received in API-Gateway Inventories Controller getInventoryVehicleAggregate with inventoryId: "+inventoryId);
        return ResponseEntity.ok().body(inventoriesService.getInventoryAggregate(inventoryId));
    }
}
