package org.io.GreenGame.inventory.service;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.repository.InventoryRepository;
import org.io.GreenGame.inventory.repository.ItemRepository;
import org.io.GreenGame.user.model.GreenGameUser;
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

    //TODO: ⥥ to chyba powinno być w module uzytkownika
    @Override
    public Boolean assignUserToInventory(GreenGameUser user) {
        Long id;
        do {
            id = new Random().nextLong();
        }
        while (inventoryRepository.checkInventoryIDInDatabase(id) != 0);
        if(userRepository.checkIfIdIsInDatabase(user.getId()) == 0 || userRepository.checkIfUsernameIsInDatabase(user.getUsername()) == 0 || userRepository.checkIfEmailIsInDatabase(user.getEmail()) == 0) {
            return false;
        }
        else {
            List<Item> items = Arrays.asList(new Item[10]);
            Inventory inventory = new Inventory(id, user.getId(), items);
            try {
                inventoryRepository.save(inventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean addItemToInventory(Inventory inventory, Item item) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventory.getId()) == 0) {
            return false;
        }
        else {
            if(itemRepository.checkItemIDInDatabase(item.getId()) == 0 && itemRepository.checkItemNameInDatabase(item.getName()) == 0) {
                try {
                    itemRepository.save(item);
                } catch (Exception e) {
                    return false;
                }
            }
            else {
                try {
                    inventory.addItem(inventory.getNextFreeSlotIndex(), item);
                    /*TODO: jestem w pociągu i nie mogę sprawdzić czy save działa też jak update ;(
                     TODO: więc musi tak pozostać na razie ;)*/
                    inventoryRepository.save(inventory);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean deleteItemFromSlot(Inventory inventory, Integer index) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventory.getId()) == 0) {
            return false;
        }
        else {
            try {
                inventory.deleteItem(index);
                 /*TODO: jestem w pociągu i nie mogę sprawdzić czy save działa też jak update ;(
                 TODO: więc musi tak pozostać na razie ;)*/
                inventoryRepository.save(inventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean moveItems(Inventory inventory, Integer index1, Integer index2) {
        if(inventoryRepository.checkInventoryIDInDatabase(inventory.getId()) == 0) {
            return false;
        }
        else {
            try {
                inventory.moveItem(index1, index2);
                inventoryRepository.save(inventory);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
