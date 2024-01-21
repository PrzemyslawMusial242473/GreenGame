package org.io.GreenGame.market.service;

import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.inventory.service.InventoryServiceImplementation;
import org.io.GreenGame.market.model.MarketItem;
import org.io.GreenGame.market.model.MarketOffer;
import org.io.GreenGame.market.model.MarketUser;
import org.io.GreenGame.market.repository.MarketItemRepository;
import org.io.GreenGame.market.repository.MarketOfferRepository;
import org.io.GreenGame.market.repository.MarketUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class MarketService {

    private MarketItemRepository marketItemRepository;
    private MarketOfferRepository marketOfferRepository;
    private MarketUserRepository marketUserRepository;
    private final InventoryServiceImplementation inventoryServiceImplementation;


    @Autowired
    public MarketService(MarketItemRepository marketItemRepository, MarketOfferRepository marketOfferRepository, MarketUserRepository marketUserRepository, InventoryServiceImplementation inventoryServiceImplementation) {

        this.inventoryServiceImplementation = inventoryServiceImplementation;
        this.marketItemRepository = marketItemRepository;
        this.marketOfferRepository = marketOfferRepository;
        this.marketUserRepository = marketUserRepository;

    }

    private void checkUser(Long id){
        MarketUser marketUser = marketUserRepository.findById(id).orElse(null);
        if (marketUser == null){
            marketUserRepository.save(new MarketUser(id));
            return;
        }

        if (marketUser.getMoney() != 0){
            inventoryServiceImplementation.modifyBalance(id,marketUser.getMoney());
            marketUser.setMoney(0);
            marketUserRepository.save(marketUser);
        }

    }

    public List<MarketOffer> getOffers(Long userID){
        checkUser(userID);
        return marketOfferRepository.getAllOffers(userID);
    }
    public List<MarketOffer> getOffersFilteredNameAsc(String key){
        return marketOfferRepository.getOfferLIKENameAsc(key);
    }
    public List<MarketOffer> getOffersFilteredNameDesc(String key){
        return marketOfferRepository.getOfferLIKENameDsc(key);
    }
    public List<MarketOffer> getOffersFilteredPriceAsc(String key){
        return marketOfferRepository.getOfferLIKEPriceAsc(key);
    }
    public List<MarketOffer> getOffersFilteredPriceDesc(String key){
        return marketOfferRepository.getOfferLIKEPriceDsc(key);
    }


    public double getUserMoney(Long userId){
        return inventoryServiceImplementation.getUserInventory(userId).getBalance();
    }

    @Transactional
    public MarketUser marketUser(Long id){
        MarketUser result = marketUserRepository.getMarketUserByID(id);
        if (result == null){
            result = new MarketUser(id,new ArrayList<>(),2000);
        }
        marketUserRepository.save(result);
        return result;
    }

    public List<MarketOffer> getMyOffers(Long userID){
        return marketOfferRepository.getUserOffers(userID);
    }
    @Transactional
    public void createOffer(Long id,String desc, double price, Long itemID){
        checkUser(id);
        MarketUser marketUser = marketUserRepository.getMarketUserByID(id);
        Item item = inventoryServiceImplementation.getItemFromInventory(id,itemID);
        MarketItem marketItem = new MarketItem(item.getName(),item.getDescription());
        MarketOffer marketOffer = new MarketOffer(marketUser,marketItem,price,desc);
        marketItemRepository.save(marketItem);
        marketOfferRepository.save(marketOffer);
        inventoryServiceImplementation.deleteItemFromInventory(id,itemID);
    }
    public List<MarketItem> getUserItems(Long id){
        List<Item> items = inventoryServiceImplementation.getUserInventory(id).getItems();
        List<MarketItem> marketItems = new ArrayList<>();
        for (Item item:items) {
            marketItems.add(new MarketItem(item.getId(), item.getName(),item.getDescription()));
        }
        return marketItems;
    }
    @Transactional
    public void buyItem(Long OfferID, Long BuyerID){
        checkUser(BuyerID);

        MarketOffer marketOffer = marketOfferRepository.getOfferByID(OfferID);
        MarketItem marketItem = marketItemRepository.getMarketItemByID(marketOffer.getItem().getId());
        MarketUser seller = marketUserRepository.getMarketUserByID(marketOffer.getSeller().getId());
        MarketUser buyer = marketUserRepository.getMarketUserByID(BuyerID);
        System.out.println(inventoryServiceImplementation.getUserInventory(BuyerID).getBalance());
        System.out.println(marketOffer.getPrice());

        if(inventoryServiceImplementation.getUserInventory(BuyerID).getBalance() < marketOffer.getPrice()){
            throw new RuntimeException("Za mało kasy");
        }

        marketOffer.setBuyer(buyer);
        marketOffer.setEnded(true);
        marketItem.setExists(false);
        Item item = new Item(inventoryServiceImplementation.getUserInventory(buyer.getId()), marketItem.getId(),
                marketItem.getName(), marketItem.getDescription(), marketOffer.getPrice());
        seller.setMoney(seller.getMoney() + marketOffer.getPrice());
        marketItemRepository.save(marketItem);
        marketOfferRepository.save(marketOffer);
        marketUserRepository.save(seller);
        marketUserRepository.save(buyer);
        inventoryServiceImplementation.addItemToInventory(BuyerID, item);
        inventoryServiceImplementation.modifyBalance(BuyerID,-marketOffer.getPrice());

    }

    @Transactional
    public void changeOffer(Long userID, Long offerID, double newPrice, String newDesc) throws RuntimeException {
        MarketOffer marketOffer = marketOfferRepository.getOfferByID(offerID);
        if(userID == marketOffer.getSeller().getId()) {
            marketOffer.setDescription(newDesc);
            marketOffer.setPrice(newPrice);
            marketOfferRepository.save(marketOffer);
        } else {
            throw new RuntimeException("Możesz modyfikować tylko swoje oferty");
        }

    }

    @Transactional
    public void deleteOffer(Long userID, Long offerID) throws RuntimeException {
        MarketOffer marketOffer = marketOfferRepository.getOfferByID(offerID);
        System.out.println(marketOffer);
        if(userID == marketOffer.getSeller().getId()) {
            Item item = new Item();
            item.setDescription(marketOffer.getItem().getDescription());
            item.setName(marketOffer.getItem().getName());
//            item.setValue(marketOffer.getItem().getValue());
            item.setValue(marketOffer.getPrice());
            item.setInventory(inventoryServiceImplementation.getUserInventory(userID));
            item.setId(marketOffer.getItem().getId());
            inventoryServiceImplementation.addItemToInventory(userID,item);
            marketOfferRepository.delete(marketOffer);
        } else {
            throw new RuntimeException("Możesz usuwać tylko swoje oferty");
        }
    }

    @Transactional
    public MarketOffer getMarketOffer(Long offerID) {
        return marketOfferRepository.getOfferByID(offerID);
    }

}
