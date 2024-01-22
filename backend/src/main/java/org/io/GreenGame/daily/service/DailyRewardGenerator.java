//package org.io.GreenGame.daily.service;
//
//import org.io.GreenGame.inventory.model.Item;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//@Service
//public class DailyRewardGenerator {
//
//    private static final List<Item> availableItems = new ArrayList<>();
//
//    static {
//
//        availableItems.add(new Item("frontend/daily-module/src/assets/amulet.png"));
//        availableItems.add(new Item("frontend/daily-module/src/assets/bomb.png"));
//        availableItems.add(new Item("frontend/daily-module/src/assets/envelope.png"));
//        availableItems.add(new Item("frontend/daily-module/src/assets/flask.png"));
//        availableItems.add(new Item("frontend/daily-module/src/assets/hatchet.png"));
//        availableItems.add(new Item("frontend/daily-module/src/assets/helmet.png"));
//        availableItems.add(new Item("frontend/daily-module/src/assets/scroll.png"));
//
//    }
//
//    public static Item generateRandomItem() {
//        if (availableItems.isEmpty()) {
//            return null;
//        }
//
//        Random random = new Random();
//        int randomIndex = random.nextInt(availableItems.size());
//        return availableItems.get(randomIndex);
//    }
//
//}
