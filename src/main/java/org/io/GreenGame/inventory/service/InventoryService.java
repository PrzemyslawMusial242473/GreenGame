package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.repository.UserRepository;

public interface InventoryService {

    Boolean assignUserToInventory(Long userID);
    Boolean addItemToInventory(Long userID, Long itemID);
    Boolean deleteItemFromSlot(Long userID, Integer index);
    Item getItemFromSlot(Long userID, Integer index);
    Item getItemFromInventory(Long userID, Long itemID);
    Boolean deleteItemFromInventory(Long userID, Long itemID);
    Boolean moveItems(Long userID, Integer index1, Integer index2);
    Inventory getUserInventory(Long userID);
    Boolean modifyBalance(Long userID, Double changeInBalance);


}
