package com.cardealership.purchaseservice.datamappinglayer;

import com.cardealership.purchaseservice.datalayer.PurchaseOrder;
import com.cardealership.purchaseservice.presentationlayer.PurchaseOrderResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseOrderResponseModelMapper {

    @Mapping(expression = "java(purchaseOrder.getPurchaseOrderIdentifier().getPurchaseOrderId())", target = "purchaseOrderId")
    @Mapping(expression = "java(purchaseOrder.getClientIdentifier().getClientId())", target = "clientId")
    @Mapping(expression = "java(purchaseOrder.getEmployeeIdentifier().getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(purchaseOrder.getInventoryIdentifier().getInventoryId())", target = "inventoryId")
    @Mapping(expression = "java(purchaseOrder.getVehicleIdentifier().getVin())", target = "vehicleId")
    PurchaseOrderResponseModel entityToResponseModel(PurchaseOrder purchaseOrder);

    List<PurchaseOrderResponseModel> entityListToResponseModelList(List<PurchaseOrder> purchaseOrders);

}
