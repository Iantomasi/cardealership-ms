package com.cardealership.purchaseservice.businesslayer;

import com.cardealership.purchaseservice.Utils.Exceptions.NotFoundException;
import com.cardealership.purchaseservice.datalayer.*;
import com.cardealership.purchaseservice.datamappinglayer.PurchaseOrderResponseModelMapper;
import com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle.Status;
import com.cardealership.purchaseservice.domainclientlayer.client.ClientResponseModel;
import com.cardealership.purchaseservice.domainclientlayer.client.ClientServiceClient;
import com.cardealership.purchaseservice.domainclientlayer.employee.EmployeeResponseModel;
import com.cardealership.purchaseservice.domainclientlayer.employee.EmployeeServiceClient;
import com.cardealership.purchaseservice.domainclientlayer.inventory.InventoryServiceClient;
import com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle.VehicleRequestModel;
import com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle.VehicleResponseModel;
import com.cardealership.purchaseservice.presentationlayer.PurchaseOrderRequestModel;
import com.cardealership.purchaseservice.presentationlayer.PurchaseOrderResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class

PurchaseOrderServiceImpl implements PurchaseOrderService{

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderResponseModelMapper purchaseOrderResponseModelMapper;
    private final ClientServiceClient clientServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    @Override
    public List<PurchaseOrderResponseModel> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        return purchaseOrderResponseModelMapper.entityListToResponseModelList(purchaseOrders);
    }

    //pattern -> orchestration -> telling microservices what to do
    @Override
    public PurchaseOrderResponseModel processClientPurchaseOrder(PurchaseOrderRequestModel purchaseOrderRequestModel, String clientId) {

        //validate the clientId by getting its data from clients-service
        ClientResponseModel clientResponseModel = clientServiceClient.getClient(clientId);
        if(clientResponseModel==null){
            throw new NotFoundException("Unknown clientId provided: "+clientId);
        }

        //validate the employeeId by getting its data from the employees-service
        EmployeeResponseModel employeeResponseModel=employeeServiceClient.getEmployee(purchaseOrderRequestModel.getEmployeeId());
        if(employeeResponseModel==null){
            throw new NotFoundException("Unknown employeeId provided: "+purchaseOrderRequestModel.getEmployeeId());
        }

        //validate that the vehicle exists in the inventory by getting the vehicle data from inventory-service
        VehicleResponseModel vehicleResponseModel = inventoryServiceClient.getVehicleByVehicleId(purchaseOrderRequestModel.getInventoryId(), purchaseOrderRequestModel.getVehicleId());
        if(vehicleResponseModel==null){
            throw new NotFoundException("Vehicle with vehicleId: "+purchaseOrderRequestModel.getVehicleId() + " is not in inventory with id: "+purchaseOrderRequestModel.getInventoryId());
        }

        //create financing agreement details value object
        FinancingAgreementDetails financingAgreementDetails= FinancingAgreementDetails.builder()
                .monthlyPaymentAmount(purchaseOrderRequestModel.getMonthlyPaymentAmount())
                .numberOfMonthlyPayments(purchaseOrderRequestModel.getNumberOfMonthlyPayments())
                .downPaymentAmount(purchaseOrderRequestModel.getDownPaymentAmount())
                .build();

        //build the purchase order
        PurchaseOrder purchaseOrder=PurchaseOrder.builder()
                .purchaseOrderIdentifier(new PurchaseOrderIdentifier())
                .inventoryIdentifier(new InventoryIdentifier(vehicleResponseModel.getInventoryId()))
                .vehicleIdentifier(new VehicleIdentifier(vehicleResponseModel.getVehicleId()))
                .clientIdentifier(new ClientIdentifier(clientResponseModel.getClientId()))
                .employeeIdentifier(new EmployeeIdentifier(employeeResponseModel.getEmployeeId()))
                .employeeFirstName(employeeResponseModel.getFirstName())
                .employeeLastName(employeeResponseModel.getLastName())
                .clientFirstName(clientResponseModel.getFirstName())
                .clientLastName(clientResponseModel.getLastName())
                .salePrice(purchaseOrderRequestModel.getSalePrice())
                .status(purchaseOrderRequestModel.getStatus())
                .vehicleMake(vehicleResponseModel.getMake())
                .vehicleModel(vehicleResponseModel.getModel())
                .financingAgreementDetails(financingAgreementDetails)
                .purchaseOfferDate(purchaseOrderRequestModel.getPurchaseOfferDate())
                .build();

        //save the purchase order
        PurchaseOrder saved=purchaseOrderRepository.save(purchaseOrder);

        //todo: implement try/catch

        //aggregate invariant - update the vehicle status based on the purchase order status
        //if purchase status is PURCHASE OFFER or PURCHASE NEGOTIATION, then vehicle status is SALE_PENDING
        //if purchase status is PURCHASE COMPLETED, then vehicle status is SOLD
        //if purchase status is PURCHASE CANCELED, then vehicle status is AVAILABLE

        Status vehicleStatus=Status.SALE_PENDING;

        switch(saved.getStatus()){
            case PURCHASE_OFFER,PURCHASE_NEGOTIATION -> vehicleStatus=Status.SALE_PENDING;
            case PURCHASE_COMPLETED -> vehicleStatus=Status.SOLD;
            case PURCHASE_CANCELLED -> vehicleStatus= Status.AVAILABLE;
        }

        //convert our vehicle response model into a vehicle request model
        VehicleRequestModel vehicleRequestModel=VehicleRequestModel.builder()
                .vin(vehicleResponseModel.getVehicleId())
                .inventoryId(vehicleResponseModel.getInventoryId())
                .status(vehicleStatus)
                .usageType(vehicleResponseModel.getUsageType())
                .year(vehicleResponseModel.getYear())
                .manufacturer(vehicleResponseModel.getManufacturer())
                .make(vehicleResponseModel.getMake())
                .model(vehicleResponseModel.getModel())
                .bodyClass(vehicleResponseModel.getBodyClass())
                .options(vehicleResponseModel.getOptions())
                .msrp(vehicleResponseModel.getPrice().getMsrp())
                .cost(vehicleResponseModel.getPrice().getCost())
                .build();

        //send update request to inventory-service
        inventoryServiceClient.updateVehicleStatus(vehicleRequestModel,vehicleRequestModel.getInventoryId(),
                vehicleRequestModel.getVin());

        return purchaseOrderResponseModelMapper.entityToResponseModel(saved);
    }


}
