package com.cardealership.purchaseservice.presentationlayer;

import com.cardealership.purchaseservice.datalayer.Status;
import com.cardealership.purchaseservice.domainclientlayer.client.ClientResponseModel;
import com.cardealership.purchaseservice.domainclientlayer.employee.Department;
import com.cardealership.purchaseservice.domainclientlayer.employee.EmployeeResponseModel;
import com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ClientPurchaseOrderControllerIntegrationTest {


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenFieldsAreValid_theReturnPurchaseOrderResponseModel() throws JsonProcessingException, URISyntaxException {

        //arrange
        PurchaseOrderRequestModel purchaseOrderRequestModel = PurchaseOrderRequestModel.builder()
                .inventoryId("d846a5a7-2e1c-4c79-809c-4f3f471e826d")
                .vehicleId("JN8AS5MTXDW375966")
                .employeeId("6d8e5f5b-8350-40ed-ac06-e484498f4f41")
                .salePrice(66500.00)
                .status(Status.PURCHASE_COMPLETED)
                .numberOfMonthlyPayments(60)
                .monthlyPaymentAmount(858.34)
                .downPaymentAmount(15000.00)
                .purchaseOfferDate(LocalDate.of(2023, 04, 10))
                .build();
        String clientId = "c3540a89-cb47-4c96-888e-ff96708db4d8";

        //required for get request in clientserviceclient
        ClientResponseModel clientResponseModel = new ClientResponseModel(clientId, "Alick", "Ucceli",
                "aucceli0@dot.gov", "73 Shoshone Road", "Barraute", "Québec", "Canada", "P0M 2T6");

        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7002/api/v1/clients/c3540a89-cb47-4c96-888e-ff96708db4d8")))
                        .andExpect(method(HttpMethod.GET))
                                .andRespond(withStatus(HttpStatus.OK)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(mapper.writeValueAsString(clientResponseModel)));


        //required for get request in employeeServiceClient
        EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel("6d8e5f5b-8350-40ed-ac06-e484498f4f41", "Dorry",
                "Gepp", "dgepp1@stanford.edu", "964-472-9806", 75000.00, 3.5, Department.Sales,
                "23320 Pankratz Park", "Barrhead", "Alberta", "Canada", "T7N 1S3");


        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7003/api/v1/employees/6d8e5f5b-8350-40ed-ac06-e484498f4f41")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employeeResponseModel)));


        //required for get in inventoryServiceClient
        Price price = new Price(73536.64, 64330.65, 15470.82);
        Option one = new Option("Wheels", "-inc: Style 1023, Increased Top Speed Limiter, Tires: 275/40R22 Performance Non Run-Flat", 1794.17);
        Option two = new Option("Seat Trim", "Castanea Chestnut, Olive Leaf Tanned Perforated Leather Upholstery", 4038.33);
        Option three = new Option("Executive Package", "-inc: Active Cruise Control w/Stop & Go, Glass & Wood Controls, Soft-Close Automatic Doors, Traffic Jam Assistant, Active Lane", 9638.32);
        List<Option> options = Arrays.asList(one, two, three);
        VehicleResponseModel vehicleResponseModel = new VehicleResponseModel("JN8AS5MTXDW375966", "d846a5a7-2e1c-4c79-809c-4f3f471e826d", com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle.Status.AVAILABLE,
                UsageType.NEW, 2013, "NISSAN MOTOR COMPANY, LTD", "NISSAN", "Rogue", "Crossover Utility Vehicle (CUV)", options, price);



        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/inventories/d846a5a7-2e1c-4c79-809c-4f3f471e826d/vehicles/JN8AS5MTXDW375966")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(vehicleResponseModel)));


        //required for put (to update status) in inventoryServiceclient
        VehicleRequestModel vehicleRequestModel = VehicleRequestModel.builder()
                .vin(vehicleResponseModel.getVehicleId())
                .inventoryId(vehicleResponseModel.getInventoryId())
                .status(com.cardealership.purchaseservice.domainclientlayer.inventory.vehicle.Status.SOLD)
                .usageType(vehicleResponseModel.getUsageType())
                .year(vehicleResponseModel.getYear())
                .manufacturer(vehicleResponseModel.getManufacturer())
                .make(vehicleResponseModel.getMake())
                .model(vehicleResponseModel.getModel())
                .bodyClass(vehicleResponseModel.getBodyClass())
                .options(vehicleResponseModel.getOptions())
                .msrp(vehicleResponseModel.getPrice().getMsrp())
                .cost(vehicleResponseModel.getPrice().getCost())
                .build();


        mockRestServiceServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:7001/api/v1/inventories/d846a5a7-2e1c-4c79-809c-4f3f471e826d/vehicles/JN8AS5MTXDW375966")))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().json(mapper.writeValueAsString(vehicleRequestModel)))
                .andRespond(withStatus(HttpStatus.OK));


        //act and assert
        String url = "api/v1/clients/" + clientId + "/purchaseorders";
        webTestClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(purchaseOrderRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PurchaseOrderResponseModel.class)
                .value((purchaseOrderResponseModel) -> {
                    assertNotNull(purchaseOrderResponseModel);
                    assertNotNull(purchaseOrderResponseModel.getPurchaseOrderId());
                    assertEquals(purchaseOrderRequestModel.getInventoryId(), purchaseOrderResponseModel.getInventoryId());
                    assertEquals(purchaseOrderRequestModel.getVehicleId(), purchaseOrderResponseModel.getVehicleId());
                    assertEquals(clientId, purchaseOrderResponseModel.getClientId());
                    assertEquals(purchaseOrderRequestModel.getEmployeeId(), purchaseOrderResponseModel.getEmployeeId());
                    assertEquals(employeeResponseModel.getFirstName(), purchaseOrderResponseModel.getEmployeeFirstName());
                    assertEquals(employeeResponseModel.getLastName(), purchaseOrderResponseModel.getEmployeeLastName());
                    assertEquals(clientResponseModel.getFirstName(), purchaseOrderResponseModel.getClientFirstName());
                    assertEquals(clientResponseModel.getLastName(), purchaseOrderResponseModel.getClientLastName());
                    assertEquals(purchaseOrderRequestModel.getSalePrice(), purchaseOrderResponseModel.getSalePrice());
                    assertEquals(purchaseOrderRequestModel.getStatus(), purchaseOrderResponseModel.getStatus());
                    assertEquals(vehicleResponseModel.getMake(), purchaseOrderResponseModel.getVehicleMake());
                    assertEquals(vehicleResponseModel.getModel(), purchaseOrderResponseModel.getVehicleModel());
                    assertEquals(purchaseOrderRequestModel.getDownPaymentAmount(), purchaseOrderResponseModel.getFinancingAgreementDetails().getDownPaymentAmount());
                    assertEquals(purchaseOrderRequestModel.getMonthlyPaymentAmount(), purchaseOrderResponseModel.getFinancingAgreementDetails().getMonthlyPaymentAmount());
                    assertEquals(purchaseOrderRequestModel.getNumberOfMonthlyPayments(), purchaseOrderResponseModel.getFinancingAgreementDetails().getNumberOfMonthlyPayments());
                    assertEquals(purchaseOrderRequestModel.getPurchaseOfferDate(), purchaseOrderResponseModel.getPurchaseOfferDate());
                });



    }




}