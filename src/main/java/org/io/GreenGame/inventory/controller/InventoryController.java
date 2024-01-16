package org.io.GreenGame.inventory.controller;

import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.service.InventoryService;
import org.io.GreenGame.user.model.GreenGameUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/assignUserToInventory")
    public ResponseEntity<String> assignUserToInventory(@RequestParam GreenGameUser user) {
        inventoryService.assignUserToInventory(user);
        return ResponseEntity.ok("User assigned to inventory");
    }

    @PostMapping("/addItemToInventory")
    public ResponseEntity<String> addItemToInventory(@RequestParam GreenGameUser user, @RequestParam Item item) {
        inventoryService.addItemToInventory(inventoryService.getUserInventory(user), item);
        return ResponseEntity.ok("Item added to inventory");
    }

    @DeleteMapping("/deleteItemFromSlot")
    public ResponseEntity<String> deleteItemFromSlot(@RequestParam GreenGameUser user, @RequestParam Integer index) {
        inventoryService.deleteItemFromSlot(inventoryService.getUserInventory(user), index);
        return ResponseEntity.ok("Item deleted from slot");
    }

    @DeleteMapping("/deleteItemFromInventory")
    public ResponseEntity<String> deleteItemFromInventory(@RequestParam GreenGameUser user, @RequestParam Item item) {
        inventoryService.deleteItemFromInventory(inventoryService.getUserInventory(user), item);
        return ResponseEntity.ok("Item deleted from inventory");
    }

    @PostMapping("/moveItems")
    public ResponseEntity<String> moveItems(@RequestParam GreenGameUser user, @RequestParam Integer index1, @RequestParam Integer index2) {
        inventoryService.moveItems(inventoryService.getUserInventory(user), index1, index2);
        return ResponseEntity.ok("Items moved");
    }

    @GetMapping("/getUserInventory")
    public ResponseEntity<String> getUserInventory(@RequestParam GreenGameUser user) {
        inventoryService.getUserInventory(user);
        return ResponseEntity.ok("User inventory");
    }

    @PostMapping("/modifyBalance")
    public ResponseEntity<String> modifyBalance(@RequestParam GreenGameUser user, @RequestParam Double newBalance) {
        inventoryService.modifyBalance(user, newBalance);
        return ResponseEntity.ok("Balance modified");
    }
}
