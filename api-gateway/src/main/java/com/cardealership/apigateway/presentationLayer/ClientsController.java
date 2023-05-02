package com.cardealership.apigateway.presentationLayer;

import com.cardealership.apigateway.businessLayer.ClientsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/clients")
public class ClientsController {

    private ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping(
            value = "/{clientId}",
            produces = "application/json"
    )
    ResponseEntity<ClientResponseModel> getClientAggregate(@PathVariable String clientId){
        log.debug("1, Received in api-gateway inventories controller getClientAggregate with clientId: " + clientId);
        return ResponseEntity.ok().body(clientsService.getClient(clientId));
    }
}
