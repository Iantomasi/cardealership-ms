package com.cardealership.apigateway.businessLayer;


import com.cardealership.apigateway.domainClientLayer.ClientServiceClient;
import com.cardealership.apigateway.presentationLayer.ClientResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientsServiceImpl implements ClientsService{
    private ClientServiceClient clientServiceClient;

    public ClientsServiceImpl(ClientServiceClient clientServiceClient) {
        this.clientServiceClient = clientServiceClient;
    }

    @Override
    public ClientResponseModel getClient(String clientId) {
        return clientServiceClient.getClient(clientId);
    }
}
