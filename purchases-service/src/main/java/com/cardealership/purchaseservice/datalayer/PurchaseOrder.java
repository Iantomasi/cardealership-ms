package com.cardealership.purchaseservice.datalayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "purchaseorders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    @Id
    private String id;

    private PurchaseOrderIdentifier purchaseOrderIdentifier;
    private InventoryIdentifier inventoryIdentifier;
    private VehicleIdentifier vehicleIdentifier;
    private ClientIdentifier clientIdentifier;
    private EmployeeIdentifier employeeIdentifier;
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
