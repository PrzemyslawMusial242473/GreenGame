package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.repository.UserRepository;

public interface InventoryService {

    Boolean assignUserToInventory(GreenGameUser user);
    Boolean addItemToInventory(Inventory inventory, Item item);
    Boolean deleteItemFromSlot(Inventory inventory, Integer index);
    Boolean deleteItemFromInventory(Inventory inventory, Item item);
    Boolean moveItems(Inventory inventory, Integer index1, Integer index2);
    Inventory getUserInventory(GreenGameUser user);
    Boolean modifyBalance(GreenGameUser greenGameUser, Double newBalance);


}
