package com.cardealership.clientservice.businesslayer;


import com.cardealership.clientservice.presentationlayer.ClientRequestModel;
import com.cardealership.clientservice.presentationlayer.ClientResponseModel;

import java.util.List;

public interface ClientService {

    ClientResponseModel addClient(ClientRequestModel clientRequestModel);
    ClientResponseModel getClientByIdentifier(String clientId);
    List<ClientResponseModel> getClients();

    ClientResponseModel updateClient(ClientRequestModel clientRequestModel, String clientId);

    void deleteClient(String clientId);
}
