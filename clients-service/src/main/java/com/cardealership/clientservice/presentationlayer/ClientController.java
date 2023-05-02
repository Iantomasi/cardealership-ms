package com.cardealership.clientservice.presentationlayer;

import com.cardealership.clientservice.businesslayer.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping()
    public List<ClientResponseModel> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/{clientId}")
    public ClientResponseModel getClientId(@PathVariable String clientId) {
        return clientService.getClientByIdentifier(clientId);
    }

    @PostMapping()
    public ClientResponseModel addClient(@RequestBody ClientRequestModel clientRequestModel) {
        return clientService.addClient(clientRequestModel);
    }

    @PutMapping("/{clientId}")
    public ClientResponseModel updateClient(@RequestBody ClientRequestModel clientRequestModel, @RequestParam String clientId){
        return clientService.updateClient(clientRequestModel, clientId);
    }


    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable String clientId){
        clientService.deleteClient(clientId);
    }

}
