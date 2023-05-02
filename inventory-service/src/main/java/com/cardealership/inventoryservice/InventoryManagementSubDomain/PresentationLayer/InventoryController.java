package com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer;

import com.cardealership.inventoryservice.InventoryManagementSubDomain.BusinessLayer.VehicleInventoryService;
import com.cardealership.inventoryservice.Utils.Exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/inventories")
public class InventoryController {

    //Field Injection
    @Autowired
    private VehicleInventoryService vehicleInventoryService;

    @GetMapping()
    public List<InventoryResponseModel> getInventories() {
        return vehicleInventoryService.getInventories();
    }

    @GetMapping("/{inventoryId}/vehicles")
    public List<VehicleResponseModel> getVehiclesInInventory(@PathVariable String inventoryId,
                                                    Map<String, String> queryParams){
        return vehicleInventoryService.getVehiclesInventoryByField(inventoryId, queryParams);
    }

    @GetMapping("/{inventoryId}")
    public VehicleInventoryResponseModel getInventoryDetails(@PathVariable String inventoryId){

        log.debug("4. Received in API-Gateway Inventory Service Client getInventoryById with inventoryId: "
                +inventoryId);

        return vehicleInventoryService.getInventoryDetails(inventoryId);
    }
    @GetMapping("/{inventoryId}/vehicles/{vehicleId}")
    public VehicleResponseModel getVehicleInInventory(@PathVariable String inventoryId,
                                              @PathVariable String vehicleId){
        return vehicleInventoryService.getVehiclesInInventoryByVehicleId(inventoryId,vehicleId);
    }

    @PostMapping()
    ResponseEntity <InventoryResponseModel> addInventory(@RequestBody InventoryRequestModel inventoryRequestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleInventoryService.addInventory(inventoryRequestModel) );
    }

    @PostMapping("/{inventoryId}/vehicles")
    ResponseEntity <VehicleResponseModel> addVehicleToInventory(@RequestBody VehicleRequestModel vehicleRequestModel,
                                               @PathVariable String inventoryId){
        if(vehicleRequestModel.getVin().length() != 17){
            throw new InvalidInputException("Invalid vin provided " + vehicleRequestModel.getVin());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleInventoryService.addVehicleToInventory(vehicleRequestModel,inventoryId));
    }
    @PutMapping("/{inventoryId}")
    ResponseEntity<InventoryResponseModel> updateInventory(@RequestBody InventoryRequestModel inventoryRequestModel,
                                           @PathVariable String inventoryId){
    return ResponseEntity.ok().body(vehicleInventoryService.updateInventory(inventoryRequestModel, inventoryId));
    }
    @PutMapping("/{inventoryId}/vehicles/{vehicleId}")
    public VehicleResponseModel updateVehicleInInventory(@RequestBody VehicleRequestModel vehicleRequestModel,
                                                  @PathVariable String inventoryId,
                                                  @PathVariable String vehicleId){
        return vehicleInventoryService.updateVehicleInInventory(vehicleRequestModel,inventoryId,vehicleId);
    }

    @DeleteMapping("/{inventoryId}/vehicles/{vehicleId}")
    void removeVehicleFromInventory(@PathVariable String inventoryId, @PathVariable String vehicleId){

        vehicleInventoryService.removeVehicleFromInventory(inventoryId,vehicleId);
    }

    @DeleteMapping("/{inventoryId}")
    ResponseEntity<Void> removeInventoryAndContent(@PathVariable String inventoryId){
        vehicleInventoryService.deleteInventoryAndContent(inventoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
