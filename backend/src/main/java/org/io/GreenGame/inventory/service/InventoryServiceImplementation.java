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
        System.out.println("user: "+ userRepository.checkIfIdIsInDatabase(12l));
        if(userRepository.checkIfIdIsInDatabase(userID)==0l) {
            return false;
        }
        else {
            List<Item> items = Arrays.asList(new Item[10]);
            /*TODO: po zmianach w Inventory (z user_id na GreenGameUser) ustawić:
             * Inventory inventory = new Inventory(id, userRepository.findUserByID(userID), items, 0.0);
             */
            Inventory inventory = new Inventory(id, userID, items, 0.0);
            try {
                inventoryRepository.save(inventory);
                System.out.println("Inventory saved");
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean addItemToInventory(Long userID, Long itemID) {

        if(inventoryRepository.findInventoryByUserID(userID) == null && itemRepository.findItemByID(itemID) == null) {
            return false;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            Item tempItem = itemRepository.findItemByID(itemID);
            Long inventoryID = tempInventory.getId();
            if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
                return false;
            }
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
    public Boolean addItemToInventory(Long userID, Item item) {


        if(inventoryRepository.findInventoryByUserID(userID) == null) {
            return false;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            Long inventoryID = tempInventory.getId();
            if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
                return false;
            }
            try {
                tempInventory.addItem(tempInventory.getNextFreeSlotIndex(), item);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteItemFromSlot(Long userID, Integer index) {
        Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
        Long inventoryID = tempInventory.getId();
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return false;
        }
        else {
            try {
                tempInventory.deleteItem(index);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteItemFromInventory(Long userID, Long itemID) {
        if(inventoryRepository.findInventoryByUserID(userID) == null || itemRepository.findItemByID(itemID) == null) {
            System.out.println("Inventory not found or no item with given ID");
            return false;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            Item tempItem = itemRepository.findItemByID(itemID);
            Long inventoryID = tempInventory.getId();
            if (inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
                return false;
            }
            try {
                tempInventory.deleteItem(tempItem);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteItemFromInventory(Long userID, Item item) {
        if(inventoryRepository.findInventoryByUserID(userID) == null) {
            System.out.println("Inventory not found");
            return false;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            Long inventoryID = tempInventory.getId();
            if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
                return false;
            }
            try {
                tempInventory.deleteItem(item);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean moveItems(Long userID, Integer index1, Integer index2) {
        Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
        Long inventoryID = tempInventory.getId();
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID )== 0) {
            return false;
        }
        else {
            try {
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
    public Item getItemFromSlot(Long userID, Integer index) {
        Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
        Long inventoryID = tempInventory.getId();
        if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
            return null;
        }
        else {
            return tempInventory.getItem(index);
        }
    }

    @Override
    public Item getItemFromInventory(Long userID, Long itemID) {
        if(inventoryRepository.findInventoryByUserID(userID) == null) {
            throw new NullPointerException("Inventory not found");
        } else if (itemRepository.findItemByID(itemID) == null) {
            throw new NullPointerException("Item not found");
        } else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            return tempInventory.getItem(itemID);
        }
    }

    @Override
    public Item getItemFromInventory(Long userID, Item item) {
        if(inventoryRepository.findInventoryByUserID(userID) == null) {
            throw new NullPointerException("Inventory not found");
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            return tempInventory.getItem(item.getId());
        }
    }
}
