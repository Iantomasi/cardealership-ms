package com.cardealership.inventoryservice.InventoryManagementSubDomain.BusinessLayer;


import com.cardealership.inventoryservice.InventoryManagementSubDomain.DataMapperLayer.*;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer.*;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory.Inventory;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory.InventoryRepository;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.*;
import com.cardealership.inventoryservice.Utils.Exceptions.DuplicateVinException;
import com.cardealership.inventoryservice.Utils.Exceptions.InvalidInputException;
import com.cardealership.inventoryservice.Utils.Exceptions.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleInventoryServiceImpl implements VehicleInventoryService{

    private InventoryRequestMapper inventoryRequestMapper;
    private InventoryRepository inventoryRepository;
    private InventoryResponseMapper inventoryResponseMapper;
    private VehicleRepository vehicleRepository;
    private VehicleResponseMapper vehicleResponseMapper;
    private VehicleRequestMapper vehicleRequestMapper;
    private VehicleInventoryResponseMapper vehicleInventoryResponseMapper;

    public VehicleInventoryServiceImpl(InventoryRequestMapper inventoryRequestMapper,
                                       InventoryRepository inventoryRepository, InventoryResponseMapper inventoryResponseMapper,
                                       VehicleRepository vehicleRepository, VehicleResponseMapper vehicleResponseMapper,
                                       VehicleRequestMapper vehicleRequestMapper,
                                       VehicleInventoryResponseMapper vehicleInventoryResponseMapper) {
        this.inventoryRequestMapper = inventoryRequestMapper;
        this.inventoryRepository = inventoryRepository;
        this.inventoryResponseMapper = inventoryResponseMapper;
        this.vehicleRepository = vehicleRepository;
        this.vehicleResponseMapper = vehicleResponseMapper;
        this.vehicleRequestMapper = vehicleRequestMapper;
        this.vehicleInventoryResponseMapper = vehicleInventoryResponseMapper;
    }

    @Override
    public List<InventoryResponseModel> getInventories() {
        return inventoryResponseMapper.entityListToResponseModelList(inventoryRepository.findAll());
    }

    @Override
    public List<VehicleResponseModel> getVehiclesInventoryByField(String inventoryId, Map<String, String> queryParams) {

        if (!inventoryRepository.existsByInventoryIdentifier_InventoryId(inventoryId)){
            return null; //throw an exception
        }

        //extract the query params
        String status = queryParams.get("status");
        String usageType = queryParams.get("Usage");

        // conversion idea
        // Status newStatus = Status.valueOf(status);

        Map<String, Status> statusMap = new HashMap<String, Status>();
        statusMap.put("available", Status.AVAILABLE);
        statusMap.put("sale_pending", Status.SALE_PENDING);
        statusMap.put("sold", Status.SOLD);

        Map<String, UsageType> usageTypeMap = new HashMap<String, UsageType>();
        usageTypeMap.put("new", UsageType.NEW);
        usageTypeMap.put("used", UsageType.USED);

        if (status != null && usageType != null){
            vehicleRepository.findAllByInventoryIdentifier_InventoryIdAndStatusEqualsAndUsageTypeEquals(inventoryId,
                    statusMap.get(status.toLowerCase()), usageTypeMap.get(usageType.toLowerCase()));
        }

        if(status != null){
            return vehicleResponseMapper.entityListToResponseModelList(vehicleRepository.findAllByInventoryIdentifier_InventoryIdAndStatusEquals(inventoryId,
                    statusMap.get(status.toLowerCase())));
        }

        if(usageType != null){
            return vehicleResponseMapper.entityListToResponseModelList(vehicleRepository.findAllByInventoryIdentifier_InventoryIdAndUsageTypeEquals(inventoryId,
                    usageTypeMap.get(usageType.toLowerCase())));
        }

        return vehicleResponseMapper.entityListToResponseModelList(
                vehicleRepository.findAllByInventoryIdentifier_InventoryId(inventoryId));

    }

    @Override
    public VehicleInventoryResponseModel getInventoryDetails(String inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId);
        if(inventory == null)
            return null;

        List<Vehicle> vehicles = vehicleRepository.findAllByInventoryIdentifier_InventoryIdAndStatusEquals(inventoryId,Status.AVAILABLE);

        List<VehicleResponseModel> vehicleResponseModels = vehicleResponseMapper.entityListToResponseModelList(vehicles);

        return vehicleInventoryResponseMapper.entitiesToResponseModel(inventory,vehicleResponseModels);
    }

    @Override
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel) {
        Inventory inventory=inventoryRequestMapper.requestModelToEntity(inventoryRequestModel);
        Inventory saved=inventoryRepository.save(inventory);

        return inventoryResponseMapper.entityToResponseModel(saved);
    }

    @Override
    public VehicleResponseModel addVehicleToInventory(VehicleRequestModel vehicleRequestModel, String inventoryId) {
        Inventory inventory=inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId);
        if(inventory==null){
            throw new NotFoundException("Unknown InventoryId provided " + inventoryId);
        }

        //convert request model to entity
        //create value objects

        VehicleIdentifier vehicleIdentifier=new VehicleIdentifier(vehicleRequestModel.getVin());
        //calculate options cost
        double totalOptionsCost=vehicleRequestModel.getOptions().stream()
                .mapToDouble(Option::getCost).sum();
        Price price=new Price(vehicleRequestModel.getMsrp(), vehicleRequestModel.getCost(), totalOptionsCost);

        Vehicle vehicle=vehicleRequestMapper.requestModelToEntity(vehicleRequestModel,vehicleIdentifier,
                inventory.getInventoryIdentifier(),price);

        try{
            return vehicleResponseMapper.entityToResponseModel(vehicleRepository.save(vehicle));
        }
        catch (DataAccessException ex) {
            if (ex.getMessage().contains("constraint [vin]") || ex.getCause().toString().contains("ConstraintViolation")){
                throw new DuplicateVinException("Inventory already contains a vehicle with VIN " +vehicleRequestModel.getVin());
            }

            throw new InvalidInputException("An unknown error has occurred");
        }
    }

    @Override
    public InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, String inventoryId) {

        Inventory existingInventory = inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId);
        Inventory inventory = inventoryRequestMapper.requestModelToEntity(inventoryRequestModel);

        if(inventory==null){
            return null; //later throw exception
        }
        inventory.setId(existingInventory.getId());

        inventory.setInventoryIdentifier(existingInventory.getInventoryIdentifier());

        Inventory inventoryTOUpdate = inventoryRepository.save(inventory);

        return inventoryResponseMapper.entityToResponseModel(inventoryRepository.save(inventoryTOUpdate));
    }

    @Override
    public VehicleResponseModel updateVehicleInInventory(VehicleRequestModel vehicleRequestModel, String inventoryId, String vehicleId) {
        Vehicle vehicle = vehicleRepository.findByVehicleIdentifier_Vin(vehicleId);

        if(inventoryRepository.existsByInventoryIdentifier_InventoryId(inventoryId) == null){
            return null;
        }

        if(vehicle == null){
            return null;
        }

        double totalOptionsCost=vehicleRequestModel.getOptions().stream()
                .mapToDouble(Option::getCost).sum();
        Price price=new Price(vehicleRequestModel.getMsrp(), vehicleRequestModel.getCost(), totalOptionsCost);

        Vehicle updateVehicle = vehicleRequestMapper.requestModelToEntity(vehicleRequestModel,vehicle.getVehicleIdentifier(),
                vehicle.getInventoryIdentifier(),price);

        updateVehicle.setId(vehicle.getId());

        return vehicleResponseMapper.entityToResponseModel(vehicleRepository.save(updateVehicle));
    }

    @Override
    public VehicleResponseModel getVehiclesInInventoryByVehicleId(String inventoryId, String vehicleId) {

        if(inventoryRepository.existsByInventoryIdentifier_InventoryId(inventoryId) == null){
            return null;
        }
        Vehicle foundVehicle =vehicleRepository.findByInventoryIdentifier_InventoryIdAndVehicleIdentifier_Vin(inventoryId,vehicleId);

        if (foundVehicle == null){
            return null;
        }
        return vehicleResponseMapper.entityToResponseModel(foundVehicle);
    }

    @Override
    public void removeVehicleFromInventory(String inventoryId, String vehicleId) {

        if (inventoryRepository.existsByInventoryIdentifier_InventoryId(inventoryId) == null){
             return;
        }

        if (vehicleRepository.findByVehicleIdentifier_Vin(vehicleId) == null){
            return;
        }
        Vehicle vehicles = vehicleRepository.findByVehicleIdentifier_Vin(vehicleId);

        vehicleRepository.delete(vehicles);

    }

    @Override
    public void deleteInventoryAndContent(String inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryIdentifier_InventoryId(inventoryId);

        if (inventory == null){
            return;
        }
        List<Vehicle> vehicles =vehicleRepository.findAllByInventoryIdentifier_InventoryId(inventoryId);

        vehicleRepository.deleteAll(vehicles);

        inventoryRepository.delete(inventory);
    }
}
