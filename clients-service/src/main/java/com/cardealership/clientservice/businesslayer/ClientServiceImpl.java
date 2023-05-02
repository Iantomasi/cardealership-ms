package com.cardealership.clientservice.businesslayer;

import com.cardealership.clientservice.datalayer.Address;
import com.cardealership.clientservice.datalayer.Client;
import com.cardealership.clientservice.datalayer.ClientRepository;
import com.cardealership.clientservice.datamapperlayer.ClientRequestMapper;
import com.cardealership.clientservice.datamapperlayer.ClientResponseMapper;
import com.cardealership.clientservice.presentationlayer.ClientRequestModel;
import com.cardealership.clientservice.presentationlayer.ClientResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ClientResponseMapper clientResponseMapper;

    private final ClientRequestMapper clientRequestMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientResponseMapper clientResponseMapper, ClientRequestMapper clientRequestMapper) {
        this.clientRepository = clientRepository;
        this.clientResponseMapper = clientResponseMapper;
        this.clientRequestMapper = clientRequestMapper;
    }

    @Override
    public ClientResponseModel addClient(ClientRequestModel clientRequestModel) {
        Client client = clientRequestMapper.entityToRequestModel(clientRequestModel);
        Address address = new Address(clientRequestModel.getStreetAddress(), clientRequestModel.getCity(),
                clientRequestModel.getProvince(), clientRequestModel.getCountry(), clientRequestModel.getPostalCode());
        client.setAddress(address);
        Client newClient = clientRepository.save(client);
        ClientResponseModel clientResponse = clientResponseMapper.entityToResponseModel(newClient);
        return clientResponse;
    }

    @Override
    public ClientResponseModel getClientByIdentifier(String clientId) {
        return clientResponseMapper.entityToResponseModel(clientRepository.findByClientIdentifier_ClientId(clientId));
    }

    @Override
    public List<ClientResponseModel> getClients() {
        return clientResponseMapper.entityListToResponseModelList(clientRepository.findAll());
    }

    @Override
    public ClientResponseModel updateClient(ClientRequestModel clientRequestModel, String clientId) {
        Client client = clientRequestMapper.entityToRequestModel(clientRequestModel);
        Client existingClient = clientRepository.findByClientIdentifier_ClientId(clientId);
        if(existingClient == null){
            return null;
        }
        client.setId(existingClient.getId());
        client.setClientIdentifier(existingClient.getClientIdentifier());
        Address address = new Address(clientRequestModel.getStreetAddress(), clientRequestModel.getCity(),
                clientRequestModel.getProvince(), clientRequestModel.getCountry(), clientRequestModel.getPostalCode());
        client.setAddress(address);
        Client updatedClient = clientRepository.save(client);
        ClientResponseModel clientResponse = clientResponseMapper.entityToResponseModel(updatedClient);
        return clientResponse;

    }

    @Override
    public void deleteClient(String clientId) {
        Client existingClient = clientRepository.findByClientIdentifier_ClientId(clientId);

        if (existingClient == null) {
            return;
        }

        clientRepository.delete(existingClient);

       }
}
