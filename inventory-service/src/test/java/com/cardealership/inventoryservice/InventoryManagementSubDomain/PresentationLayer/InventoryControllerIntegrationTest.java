package com.cardealership.inventoryservice.InventoryManagementSubDomain.PresentationLayer;

import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory.InventoryRepository;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.Option;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.Status;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.UsageType;
import com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Vehicle.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
//Resets our code everytime
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql({"/data-h2.sql"})
class InventoryControllerIntegrationTest {
    private final String BASE_URI_INVENTORY = "/api/v1/inventories";
    private final String VALID_INVENTORY_ID = "d846a5a7-2e1c-4c79-809c-4f3f471e826d";
    private final String VALID_INVENTORY_TYPE = "vehicles";
    private final String VALID_VIN = "JN8AS5MTXDW375966";


    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    InventoryRepository inventoryRepository;
//    @Autowired
//    InventoryRepository inventoryRepository;
    @Autowired
    WebTestClient webTestClient;

    //runs after each Test
//    @AfterEach
//    public void tearDown(){
//        inventoryRepository.deleteAll();
//    }

    @Test
    public void whenInventoriesExist_thenReturnAllInventories() {
        Integer expectedNumInventories = 2;

        webTestClient.get()
                .uri(BASE_URI_INVENTORY)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumInventories);
    }

    //get by id
    @Test
    public void whenInventoryWithValidIdExists_thenReturnInventory(){
        webTestClient.get()
                .uri(BASE_URI_INVENTORY + "/" +VALID_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.inventoryId").isEqualTo(VALID_INVENTORY_ID)
                .jsonPath("$.type").isEqualTo(VALID_INVENTORY_TYPE);
    }

    //Post
    @Test
    public void whenCreateInventoryWithVehicles_thenReturnNewInventory(){
        //arrange
        String expectedType = "trucks";
        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel(expectedType);

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_INVENTORY)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.inventoryId").isNotEmpty()
                .jsonPath("$.type").isEqualTo(expectedType);
    }

    //update
    //put
    @Test
    public void WhenUpdateInventoryWithValidValues_thenReturnNewInventory() {
        //arrange
        String expectedType = "trucks";

        InventoryRequestModel inventoryRequestModel = new InventoryRequestModel(expectedType);

        //act and assert
        webTestClient.put().uri(BASE_URI_INVENTORY + "/" + VALID_INVENTORY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inventoryRequestModel).accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.inventoryId")
                .isEqualTo(VALID_INVENTORY_ID)
                .jsonPath("$.type").isEqualTo(expectedType);
    }


    //delete
    @Test
    public void WhenDeleteExistingInventory_thenDeleteInventoryAndAllVehiclesInInventory() {
        Integer actualNumBefore = 5;
        Integer expectedNumAfter = 0;

        assertEquals(actualNumBefore,
                vehicleRepository.
                        findAllByInventoryIdentifier_InventoryId(VALID_INVENTORY_ID).size());

        webTestClient.delete().uri(BASE_URI_INVENTORY + "/" + VALID_INVENTORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isNoContent();

        assertEquals(expectedNumAfter,
                vehicleRepository.
                        findAllByInventoryIdentifier_InventoryId(VALID_INVENTORY_ID).size());

        assertNull(vehicleRepository.findByVehicleIdentifier_Vin(VALID_VIN));

        assertFalse(inventoryRepository.existsByInventoryIdentifier_InventoryId(VALID_INVENTORY_ID));


    }


    @Test
    public void WhenAddVehicleToInventoryWithValidInventoryId_ValidVin_ThenReturnNewVehicle(){
        String newVin = "JH4KA7551MC045888";
        Double expectedTotalOptionCost = 1900.00;

        VehicleRequestModel vehicleRequestModel = createNewVehicleRequestModel(VALID_INVENTORY_ID,newVin,Status.AVAILABLE);

        webTestClient.post().uri(BASE_URI_INVENTORY + "/"+ VALID_INVENTORY_ID + "/vehicles")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .bodyValue(vehicleRequestModel).exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(VehicleResponseModel.class)
                .value((dto)->{
                    assertNotNull(dto);
                    assertEquals(dto.getInventoryId(), VALID_INVENTORY_ID);
                    assertEquals(dto.getVehicleId(), newVin);
                    assertEquals(dto.getPrice().getTotalOptionsCost(), expectedTotalOptionCost);
                });
    }


    private VehicleRequestModel createNewVehicleRequestModel(String inventoryId, String vin, Status status){
        Option one = new Option("option 1", "luxury tires", 700.00);
        Option two = new Option("option 2", "luxury seats", 1200.00);

        List<Option> options = new ArrayList<>(Arrays.asList(one,two));

        VehicleRequestModel vehicleRequestModel = VehicleRequestModel.builder().vin(vin)
                .inventoryId(inventoryId)
                .status(status)
                .usageType(UsageType.NEW)
                .year(2023)
                .manufacturer("Toyota Canada")
                .make("Toyota")
                .model("Corolla")
                .bodyClass("Sedan")
                .msrp(45000.00)
                .cost(40000.00)
                .options(options)
                .build();

        return vehicleRequestModel;
    }
}