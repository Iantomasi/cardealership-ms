package com.cardealership.purchaseservice.presentationlayer;

import com.cardealership.purchaseservice.businesslayer.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/clients/{clientId}/purchaseorders")
@RequiredArgsConstructor
public class ClientPurchaseOrderController {
    //must be initialized at declaration
    private final PurchaseOrderService purchaseOrderService;

    @PostMapping()
    ResponseEntity<PurchaseOrderResponseModel> processClientPurchaseOrder(
            @RequestBody PurchaseOrderRequestModel purchaseOrderRequestModel,
            @PathVariable String clientId){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseOrderService.processClientPurchaseOrder(purchaseOrderRequestModel,clientId));
    }
}
