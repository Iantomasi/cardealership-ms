package com.cardealership.apigateway.businessLayer;


import com.cardealership.apigateway.presentationLayer.ClientResponseModel;

public interface ClientsService {
    ClientResponseModel getClient(String clientId);
}
