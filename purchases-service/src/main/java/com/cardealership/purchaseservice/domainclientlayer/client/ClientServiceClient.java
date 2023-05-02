package com.cardealership.purchaseservice.domainclientlayer.client;

import com.cardealership.purchaseservice.Utils.Exceptions.InvalidInputException;
import com.cardealership.purchaseservice.Utils.Exceptions.NotFoundException;
import com.cardealership.purchaseservice.Utils.HttpErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class ClientServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CLIENT_SERVICE_BASE_URL;

    public ClientServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.clients-service.host}") String clientServiceHost,
                               @Value("${app.clients-service.port}") String clientServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.CLIENT_SERVICE_BASE_URL = "http://" + clientServiceHost + ":" + clientServicePort + "/api/v1/clients";
    }

    public ClientResponseModel getClient(String clientId) {
        ClientResponseModel clientResponseModel;
        try {
            String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
            clientResponseModel = restTemplate
                    .getForObject(url, ClientResponseModel.class);

            log.debug("5. Received in API-Gateway Client Service Client getClientAggregate with clientResponseModel : "
                    + clientResponseModel.getClientId());

        }
        catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return clientResponseModel;
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}
