package org.io.GreenGame.daily.service;

import org.io.GreenGame.daily.model.DailyReward;
import org.io.GreenGame.daily.repository.DailyRewardRepository;
import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.repository.InventoryRepository;
import org.io.GreenGame.inventory.service.InventoryServiceImplementation;
import org.io.GreenGame.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DailyRewardServiceImpl implements DailyRewardService{
    @Autowired
    private DailyRewardRepository dailyRewardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryServiceImplementation inventoryServiceImplementation;

//    public DailyRewardService(DailyRewardRepository dailyRewardRepository) {
//        this.dailyRewardRepository = dailyRewardRepository;
//    }


    @Override
    public void claimDailyReward(Long userID) {
        List<DailyReward> userDailyRewards = dailyRewardRepository.getDailyRewardsByUserId(userID);

        // Sprawdź czy dzisiejsza nagroda nie została już odebrana
        if (isDailyRewardClaimedToday(userDailyRewards)) {
            System.out.println("Dzienna nagroda została już odebrana.");
        } else {
            // Jeśli nagroda jeszcze nie została odebrana, dodaj nową nagrodę i zaktualizuj dane gracza
            DailyReward newDailyReward = new DailyReward(userID, new Date(), true);
            //dailyRewardRepository.saveDailyReward(newDailyReward);

            // Dodaj nagrody za codzienną aktywność
            addDailyRewards(userID);

            System.out.println("Dzienna nagroda została pomyślnie odebrana.");
        }
    }

    @Override
    public boolean isDailyRewardClaimedToday(List<DailyReward> userDailyRewards) {
        Date today = new Date();
        for (DailyReward dailyReward : userDailyRewards) {
            if (dailyReward.getDate().equals(today)) {
                return true;
            }
        }
        return false;
    }
//    private List<Item> availableItems = new ArrayList<>();
//    static {
//        availableItems.add(new Item(inventoryServiceImplementation.getUserInventory(userId));
//    }
//    Item item1 = new Item(inventoryServiceImplementation.getUserInventory(userId));

    @Override
    public void addDailyRewards(Long userID) {
        if(inventoryServiceImplementation.getUserInventory(userID) == null) {
//            Inventory inventory = new Inventory();
            inventoryServiceImplementation.assignUserToInventory(userID);
        }
        Item item = new Item(inventoryServiceImplementation.getUserInventory(userID), 555L, "Diamentowy miecz", "ekologiczne", 511);
        inventoryServiceImplementation.addItemToInventory(userID, item);
//        Item item = new Item(inventoryServiceImplementation.getUserInventory(buyer.getId()), marketItem.getId(),
//                marketItem.getName(), marketItem.getDescription(), marketOffer.getPrice());
//        inventoryServiceImplementation.addItemToInventory(BuyerID, item.getId());

//        DailyRewardGenerator.Item randomItem = DailyRewardGenerator.generateRandomItem();
//        UUID itemId = randomItem.getId();

        // Inventory.addItemToInventory(userID,itemId);
    }
//    public static Item generateRandomItem() {
//        if (availableItems.isEmpty()) {
//            return null;
//        }
//
//        Random random = new Random();
//        int randomIndex = random.nextInt(availableItems.size());
//        return availableItems.get(randomIndex);
//    }
}
