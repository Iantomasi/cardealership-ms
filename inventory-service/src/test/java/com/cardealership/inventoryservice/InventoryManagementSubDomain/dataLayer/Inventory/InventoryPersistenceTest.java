package com.cardealership.inventoryservice.InventoryManagementSubDomain.dataLayer.Inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InventoryPersistenceTest {
    @Autowired
    InventoryRepository inventoryRepository;

    private Inventory preSavedInventory;
    @BeforeEach
    public void setup(){
        inventoryRepository.deleteAll();
        preSavedInventory = inventoryRepository.save(new Inventory("small-vehicles"));
    }
    @Test
    public void saveNewInventory_shouldSucceed(){
        //arrange
        String expectedType = "vehicles";
        Inventory newInventory = new Inventory(expectedType);

        //act
        Inventory savedInventory = inventoryRepository.save(newInventory);

        //assert
        assertNotNull(savedInventory);

        assertNotNull(savedInventory.getId());

        assertNotNull(savedInventory.getInventoryIdentifier().getInventoryId());

        assertNotNull(expectedType, savedInventory.getType());
    }

    @Test
    public void updateInventory_shouldSucceed(){
        //arrange
        String updatedType = "big-vehicles";
        preSavedInventory.setType(updatedType);

        //act
        Inventory savedInventory = inventoryRepository.save(preSavedInventory);
        //assert
        assertNotNull(savedInventory);
        assertThat(savedInventory, samePropertyValuesAs(preSavedInventory));

    }

    //delete
    @Test
    public void deleteInventoryByIdentifier_InventoryId_shouldSucceed(){
        //act
        inventoryRepository.delete(preSavedInventory);

        //assert
        assertFalse(inventoryRepository.existsByInventoryIdentifier_InventoryId(
                preSavedInventory.getInventoryIdentifier().getInventoryId()));
    }

    //find by InventoryId
    @Test
    public void findByInventoryIdentifier_InventoryId_ShouldSucceed(){
        Inventory found = inventoryRepository.findByInventoryIdentifier_InventoryId(
                preSavedInventory.getInventoryIdentifier().getInventoryId());

        //assert
        assertNotNull(found);
        assertThat(preSavedInventory, samePropertyValuesAs(found));
    }

    //find by inventoryId that is invalid
    @Test
    public void findByInvalidInventoryIdentifier_InventoryId_ShouldReturnNull(){
        //act
        Inventory found = inventoryRepository.findByInventoryIdentifier_InventoryId(
                preSavedInventory.getInventoryIdentifier().getInventoryId() + 1);

        //assert
        assertNull(found);
    }

    //check if the inventory exists
    @Test
    public void existInventoryIdentifier_InventoryId_ShouldReturnTrue(){
        //act
        Boolean found = inventoryRepository
                .existsByInventoryIdentifier_InventoryId(
                        preSavedInventory.getInventoryIdentifier().getInventoryId());
        //assert
        assertTrue(found);
    }

    //check if the inventory does not exist
    @Test
    public void existInventoryIdentifier_InventoryId_ShouldReturnFalse(){
        //act
        Boolean found = inventoryRepository
                .existsByInventoryIdentifier_InventoryId(
                        preSavedInventory.getInventoryIdentifier().getInventoryId() + 1);
        //assert
        assertFalse(found);
    }
}