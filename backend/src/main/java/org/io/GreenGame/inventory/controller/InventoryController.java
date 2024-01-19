package org.io.GreenGame.inventory.controller;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.service.InventoryService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secured/api/inventory")
public class InventoryController {
    //Wygenerowane przez copilota

    @Autowired
    private final InventoryService inventoryService;

    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    private Long getIdOfLoggedUser(){
        return authServiceImplementation.getUserFromSession().getId();
    }

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/assignUser")
    public ResponseEntity<Boolean> assignUserToInventory() {
        System.out.println("assigning inventory to user " + getIdOfLoggedUser());
        return ResponseEntity.ok(inventoryService.assignUserToInventory(getIdOfLoggedUser()));
    }

    @PostMapping("/addItem/{userID}/{itemID}")
    public ResponseEntity<Boolean> addItemToInventory(@PathVariable Long userID, @PathVariable Long itemID) {
        return ResponseEntity.ok(inventoryService.addItemToInventory(userID, itemID));
    }

    @PostMapping("/addItem/{userID}")
    public ResponseEntity<Boolean> addItemToInventory(@PathVariable Long userID, @RequestBody Item item) {
        return ResponseEntity.ok(inventoryService.addItemToInventory(userID, item));
    }

    @DeleteMapping("/deleteItemFromSlot/{userID}/{index}")
    public ResponseEntity<Boolean> deleteItemFromSlot(@PathVariable Long userID, @PathVariable Integer index) {
        return ResponseEntity.ok(inventoryService.deleteItemFromSlot(userID, index));
    }

    @GetMapping("/getItemFromSlot/{userID}/{index}")
    public ResponseEntity<Item> getItemFromSlot(@PathVariable Long userID, @PathVariable Integer index) {
        return ResponseEntity.ok(inventoryService.getItemFromSlot(userID, index));
    }

    @GetMapping("/getItemFromInventory/{userID}/{itemID}")
    public ResponseEntity<Item> getItemFromInventory(@PathVariable Long userID, @PathVariable Long itemID) {
        return ResponseEntity.ok(inventoryService.getItemFromInventory(userID, itemID));
    }

    @GetMapping("/getItemFromInventory/{userID}")
    public ResponseEntity<Item> getItemFromInventory(@PathVariable Long userID, @RequestBody Item item) {
        return ResponseEntity.ok(inventoryService.getItemFromInventory(userID, item));
    }

    @DeleteMapping("/deleteItemFromInventory/{userID}/{itemID}")
    public ResponseEntity<Boolean> deleteItemFromInventory(@PathVariable Long userID, @PathVariable Long itemID) {
        return ResponseEntity.ok(inventoryService.deleteItemFromInventory(userID, itemID));
    }

    @DeleteMapping("/deleteItemFromInventory/{userID}")
    public ResponseEntity<Boolean> deleteItemFromInventory(@PathVariable Long userID, @RequestBody Item item) {
        return ResponseEntity.ok(inventoryService.deleteItemFromInventory(userID, item));
    }

    @PostMapping("/moveItems/{userID}/{index1}/{index2}")
    public ResponseEntity<Boolean> moveItems(@PathVariable Long userID, @PathVariable Integer index1, @PathVariable Integer index2) {
        return ResponseEntity.ok(inventoryService.moveItems(userID, index1, index2));
    }

    @GetMapping("/getUserInventory")
    public ResponseEntity<Inventory> getUserInventory() {
        System.out.println("widzisz mnie");
        System.out.println(inventoryService.getUserInventory(getIdOfLoggedUser()));
        return ResponseEntity.ok(inventoryService.getUserInventory(getIdOfLoggedUser()));
    }

    @PostMapping("/modifyBalance/{userID}/{changeInBalance}")
    public ResponseEntity<Boolean> modifyBalance(@PathVariable Long userID, @PathVariable Double changeInBalance) {
        return ResponseEntity.ok(inventoryService.modifyBalance(userID, changeInBalance));
    }
}