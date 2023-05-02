package com.cardealership.purchaseservice.presentationlayer;

import com.cardealership.purchaseservice.businesslayer.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/purchaseorders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;

    @GetMapping
    ResponseEntity<List<PurchaseOrderResponseModel>> getALlPurchases(){
        return ResponseEntity.ok().body(purchaseOrderService.getAllPurchaseOrders());
    }
}
