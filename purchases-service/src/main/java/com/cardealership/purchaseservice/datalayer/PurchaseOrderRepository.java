package com.cardealership.purchaseservice.datalayer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder,String> {

}
