package com.cardealership.purchaseservice.presentationlayer;

import com.cardealership.purchaseservice.datalayer.FinancingAgreementDetails;
import com.cardealership.purchaseservice.datalayer.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Value
public class PurchaseOrderRequestModel {

    String inventoryId;
    String vehicleId;
    String employeeId;
    Double salePrice;
    Status status;
    Integer numberOfMonthlyPayments;
    Double monthlyPaymentAmount;
    Double downPaymentAmount;
    LocalDate purchaseOfferDate;
}
