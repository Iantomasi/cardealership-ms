package com.cardealership.purchaseservice.presentationlayer;

import com.cardealership.purchaseservice.datalayer.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PurchaseOrderResponseModel {

    private String purchaseOrderId;
    private String inventoryId;
    private String vehicleId;
    private String clientId;
    private String employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String clientFirstName;
    private String clientLastName;
    private Double salePrice;
    private Status status;
    private String vehicleMake;
    private String vehicleModel;
    private FinancingAgreementDetails financingAgreementDetails;
    private LocalDate purchaseOfferDate;
}
