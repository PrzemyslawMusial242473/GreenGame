package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.repository.InventoryRepository;
import org.io.GreenGame.inventory.repository.ItemRepository;
import org.io.GreenGame.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
@Service
public class InventoryServiceImplementation implements InventoryService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Boolean assignUserToInventory(Long userID) {
        Long id;
        do {
            id = new Random().nextLong();
        }
        while (inventoryRepository.checkInventoryIDInDatabase(id) != 0);
        if(userRepository.checkIfIdIsInDatabase(userID)==0) {
            return false;
        }
        else {
            List<Item> items = Arrays.asList(new Item[10]);
            Inventory inventory = new Inventory(id, userID, items, 0.0);
            try {
                inventoryRepository.save(inventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean addItemToInventory(Long inventoryID, Long itemID) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return false;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByID(inventoryID);
            Item tempItem = itemRepository.findItemByID(itemID);
            try {
                    tempInventory.addItem(tempInventory.getNextFreeSlotIndex(), tempItem);
                    inventoryRepository.save(tempInventory);
                } catch (Exception e) {
                    return false;
                }
        }
        return true;
    }

    @Override
    public Boolean deleteItemFromSlot(Long inventoryID, Integer index) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return false;
        }
        else {
            try {
                Inventory tempInventory = inventoryRepository.findInventoryByID(inventoryID);
                tempInventory.deleteItem(index);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteItemFromInventory(Long inventoryID, Long itemID) {
        if (inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return false;
        } else {
            try {
                Inventory tempInventory = inventoryRepository.findInventoryByID(inventoryID);
                Item tempItem = itemRepository.findItemByID(itemID);
                tempInventory.deleteItem(tempItem);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean moveItems(Long inventoryID, Integer index1, Integer index2) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID )== 0) {
            return false;
        }
        else {
            try {
                Inventory tempInventory = inventoryRepository.findInventoryByID(inventoryID);
                tempInventory.moveItem(index1, index2);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Inventory getUserInventory(Long userID) {
        if(userRepository.checkIfIdIsInDatabase(userID) == 0) {
            return null;
        }
        else {
            return inventoryRepository.findInventoryByUserID(userID);
        }
    }

    @Override
    public Boolean modifyBalance(Long userID, Double changeInBalance) {
        if(userRepository.checkIfIdIsInDatabase(userID) == 0) {
            return false;
        }
        else {
            try {
                Inventory temp_inventory = getUserInventory(userID);
                temp_inventory.setBalance(temp_inventory.getBalance()+changeInBalance);
                inventoryRepository.save(temp_inventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Item getItemFromSlot(Long inventoryID, Integer index) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return null;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByID(inventoryID);
            return tempInventory.getItem(index);
        }
    }

    @Override
    public Item getItemFromInventory(Long inventoryID, Long itemID) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return null;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByID(inventoryID);
            return tempInventory.getItem(itemID);
        }
    }
}
