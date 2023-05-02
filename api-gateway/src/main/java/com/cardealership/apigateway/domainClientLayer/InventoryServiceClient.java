package com.cardealership.apigateway.domainClientLayer;

import com.cardealership.apigateway.Utils.Exceptions.InvalidInputException;
import com.cardealership.apigateway.Utils.Exceptions.NotFoundException;
import com.cardealership.apigateway.Utils.HttpErrorInfo;
import com.cardealership.apigateway.presentationLayer.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Component
public class InventoryServiceClient {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private final String INVENTORY_SERVICE_BASE_URL;
    public InventoryServiceClient(RestTemplate restTemplate,
                                  ObjectMapper objectMapper,
                                  @Value("${app.inventory-service.host}") String inventoryServiceHost,
                                  @Value("${app.inventory-service.port}") String inventoryServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.INVENTORY_SERVICE_BASE_URL =
                "http://" + inventoryServiceHost+":"+inventoryServicePort+"/api/v1/inventories";

    }

    public InventoryResponseModel[] getAllInventoriesAggregate() {
        InventoryResponseModel[] inventoryResponseModels;
        try {
            String url = INVENTORY_SERVICE_BASE_URL;
            inventoryResponseModels = restTemplate.getForObject(url, InventoryResponseModel[].class);
            log.debug("5. Received in API-Gateway Inventory Service Client getAllInventoriesAggregate");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return inventoryResponseModels;
    }

    public VehicleInventoryResponseModel getInventoryAggregate(String inventoryId) {
        VehicleInventoryResponseModel vehicleInventoryResponseModel;
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId;
            vehicleInventoryResponseModel = restTemplate
                    .getForObject(url, VehicleInventoryResponseModel.class);

            log.debug("5. Received in API-Gateway Inventory Service Client getInventoryAggregate with VehicleInventoryResponseModel : " + vehicleInventoryResponseModel.getInventoryId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return vehicleInventoryResponseModel;
    }

    public VehicleResponseModel getVehicleByVehicleId(String inventoryId, String vin){
        try {
            //"/{inventoryId}/vehicles/{vin}"
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId + "/" + "vehicles" + "/" + vin;
            VehicleResponseModel vehicleResponseModel = restTemplate.getForObject(url, VehicleResponseModel.class);
            return vehicleResponseModel;
        }catch (HttpClientErrorException ex) {
            //log.debug("5. Received in API-Gateway Client Service Client getAllClients exception: " + ex.getMessage());
            throw handleHttpClientException(ex);
        }
    }
    public InventoryResponseModel addInventoryAggregate(InventoryRequestModel inventoryRequestModel){
        InventoryResponseModel inventoryResponseModel;
        try {
            String url = INVENTORY_SERVICE_BASE_URL;
            inventoryResponseModel =
                    restTemplate.postForObject(url, inventoryRequestModel,
                            InventoryResponseModel.class);

            log.debug("5. Received in API-Gateway Inventory Service Client addInventoryAggregate with type " + inventoryRequestModel.getType());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return inventoryResponseModel;
    }

    public void updateInventoryAggregate(String inventoryId, InventoryRequestModel inventoryRequestModel){
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId;
            restTemplate.put(url, inventoryRequestModel);
            log.debug("5. Received in API-Gateway Inventory Service Client updateInventoryAggregate with type " + inventoryRequestModel.getType());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
    }

    public VehicleResponseModel addVehicleToInventory(String inventoryId, VehicleRequestModel newVehicle){
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId + "/vehicles";
            VehicleResponseModel vehicleResponseModel = restTemplate.postForObject(url, newVehicle, VehicleResponseModel.class);
            return vehicleResponseModel;
        }
        catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }
    public void modifyVehicleInInventory(String inventoryId, String vehicleId, VehicleRequestModel vehicleRequestModel){
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId + "/vehicles/" + vehicleId;
            restTemplate.put(url, vehicleRequestModel);
        }
        catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public void deleteVehicleInInventory(String inventoryId, String vehicleId) {
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId + "/vehicles/" + vehicleId;
            restTemplate.delete(url);
            //log.debug("5. Received in API-Gateway Inventory Service Client deleteInventory with inventoryId : " + inventoryId);
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
    }

    public void deleteInventory(String inventoryId) {
        try {
            String url = INVENTORY_SERVICE_BASE_URL + "/" + inventoryId;
            restTemplate.delete(url);
            log.debug("5. Received in API-Gateway Inventory Service Client deleteInventory with inventoryId : " + inventoryId);
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
    }


    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}

