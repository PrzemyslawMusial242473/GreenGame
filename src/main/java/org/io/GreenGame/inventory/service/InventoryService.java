package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.repository.UserRepository;

public interface InventoryService {

    Boolean assignUserToInventory(Long userID);
    Boolean addItemToInventory(Inventory inventory, Item item);
    Boolean deleteItemFromSlot(Inventory inventory, Integer index);
    Boolean deleteItemFromInventory(Inventory inventory, Item item);
    Boolean moveItems(Inventory inventory, Integer index1, Integer index2);
    Inventory getUserInventory(Long userID);
    Boolean modifyBalance(Long userID, Double newBalance);


}
