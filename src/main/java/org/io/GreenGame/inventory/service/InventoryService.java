package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.repository.UserRepository;

public interface InventoryService {

    Boolean assignUserToInventory(Long userID);
    Boolean addItemToInventory(Long inventoryID, Long itemID);
    Boolean deleteItemFromSlot(Long inventoryID, Integer index);
    Boolean deleteItemFromInventory(Long inventoryID, Long itemID);
    Boolean moveItems(Long inventoryID, Integer index1, Integer index2);
    Inventory getUserInventory(Long userID);
    Boolean modifyBalance(Long userID, Double newBalance);


}
