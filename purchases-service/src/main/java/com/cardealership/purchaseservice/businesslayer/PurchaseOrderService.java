package com.cardealership.purchaseservice.businesslayer;

import com.cardealership.purchaseservice.presentationlayer.PurchaseOrderRequestModel;
import com.cardealership.purchaseservice.presentationlayer.PurchaseOrderResponseModel;

import java.util.List;

public interface PurchaseOrderService {

    List<PurchaseOrderResponseModel> getAllPurchaseOrders();
    PurchaseOrderResponseModel processClientPurchaseOrder(PurchaseOrderRequestModel purchaseOrderRequestModel, String clientId);

}
