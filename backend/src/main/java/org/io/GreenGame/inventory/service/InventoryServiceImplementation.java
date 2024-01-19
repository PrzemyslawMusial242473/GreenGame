package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.repository.InventoryRepository;
import org.io.GreenGame.inventory.repository.ItemRepository;
import org.io.GreenGame.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        System.out.println("user: "+ userRepository.checkIfIdIsInDatabase(12L));
        if(userRepository.checkIfIdIsInDatabase(userID) == 0L) {
            System.out.println("No user in database");
            return false;
        }
        else if(inventoryRepository.findInventoryByUserID(userID) != null) {
            System.out.println("User already has inventory");
            return false;
        }
        else {
            List<Item> items = new ArrayList<>();
            /*TODO: po zmianach w Inventory (z user_id na GreenGameUser) ustawić:
             * Inventory inventory = new Inventory(id, userRepository.findUserByID(userID), items, 0.0);
             */
            Inventory inventory = new Inventory(id, userID, items, 0.0);
            try {
                inventoryRepository.save(inventory);
                System.out.println("Inventory saved");
            } catch (Exception e) {
                System.out.println("Exception while saving inventory");
                e.printStackTrace();
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
                if(itemRepository.checkItemIDInDatabase(itemID)==0) {
                    System.out.println("Item not found by id");
                    return false;
                }
                    tempInventory.addItem(tempItem);
                    inventoryRepository.save(tempInventory);
                } catch (Exception e) {
                    return false;
                }
        }
        return true;
    }


//    fetch('http://localhost:8080/secured/api/inventory/addItem/1', {
//        method: 'POST',
//                headers: {
//            'Content-Type': 'application/json',
//        },
//        body: JSON.stringify({
//                id: 1,
//                name: 'Vodka',
//                description: 'Alcohol: 40percent',
//                value: 21.37,
//  }),
//    })
//            .then(response => response.json())
//            .then(data => console.log(data))
//            .catch(error => console.error('Error:', error));
//    //dodawanie przez endpoint w przegladarce

    @Override
    public Boolean addItemToInventory(Long userID, Item item) {

        System.out.println("item xd: "+item.toString());
        if(inventoryRepository.findInventoryByUserID(userID) == null) {
            System.out.println("Inventory not found by user id");
            return false;
        }
        else {
            Inventory tempInventory = inventoryRepository.findInventoryByUserID(userID);
            Long inventoryID = tempInventory.getId();
            if(inventoryRepository.checkInventoryIDInDatabase(inventoryID) == 0) {
                System.out.println("Inventory not found by id");
                return false;
            }
            try {
                if(itemRepository.checkItemIDInDatabase(item.getId()) == 0) {
                    System.out.println("Item not found by id");
                    System.out.println("item xd: "+item);
                    itemRepository.save(item);
                }
                tempInventory.addItem(item);
                inventoryRepository.save(tempInventory);
                System.out.println("After update: "+inventoryRepository.findInventoryByUserID(userID).toString());
            } catch (Exception e) {
                System.out.println("Exception while saving inventory");
                e.printStackTrace();  // Dodaj tę linię, aby wyświetlić informacje o błędzie
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
                itemRepository.save(tempItem);
                inventoryRepository.save(tempInventory);
            } catch (Exception e) {
                System.out.println("Exception while saving inventory");
                e.printStackTrace();
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
                if(itemRepository.checkItemIDInDatabase(item.getId()) == 0) {
                    System.out.println("Item not found by id");
                    System.out.println("item xd: "+item);
                    itemRepository.save(item);
                }
                tempInventory.deleteItem(item);
                System.out.println("tempInventory after update: "+tempInventory.toString());
                itemRepository.save(item);
                inventoryRepository.save(tempInventory);
                System.out.println("After update: "+inventoryRepository.findInventoryByUserID(userID).toString());
            } catch (Exception e) {
                System.out.println("Exception while saving inventory");
                e.printStackTrace();  // Dodaj tę linię, aby wyświetlić informacje o błędzie
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
            System.out.println("No user in database");
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
