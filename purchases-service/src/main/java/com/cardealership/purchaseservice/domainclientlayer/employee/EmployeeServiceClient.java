package com.cardealership.purchaseservice.domainclientlayer.employee;

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
public class EmployeeServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String EMPLOYEE_SERVICE_BASE_URL;

    public EmployeeServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.employees-service.host}") String employeeServiceHost,
                               @Value("${app.employees-service.port}") String employeeServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.EMPLOYEE_SERVICE_BASE_URL = "http://" + employeeServiceHost + ":" + employeeServicePort + "/api/v1/employees";
    }

    public EmployeeResponseModel getEmployee(String employeeId) {
        EmployeeResponseModel employeeResponseModel;
        try {
            String url = EMPLOYEE_SERVICE_BASE_URL + "/" + employeeId;
            employeeResponseModel = restTemplate
                    .getForObject(url, EmployeeResponseModel.class);

            log.debug("5. Received in API-Gateway Employee Service Client getEmployeeAggregate with employeeResponseModel : " + employeeResponseModel.getEmployeeId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return employeeResponseModel;
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
