package com.cardealership.purchaseservice.datalayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FinancingAgreementDetails {
    private Integer numberOfMonthlyPayments;
    private Double monthlyPaymentAmount;
    private Double downPaymentAmount;
}
