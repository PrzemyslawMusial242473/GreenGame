package org.io.GreenGame.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Inventories")
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "inventory")
    private List<Item> items = Arrays.asList(new Item[10]);

    //money money money
    @Column(name="balance")
    private double balance;


    //add item to next free slot
    public void addItem(Item item) {
        addItem(getNextFreeSlotIndex(), item);
    }

    //add item to specific slot
    public void addItem(int slotIndex, Item item) {
        items.set(slotIndex, item);
    }

    //Delete by slot id
    public void deleteItem(int slotIndex) {
//        items.set(slotIndex, null);
        items.remove(slotIndex);
    }

    //Delete by item
    public void deleteItem(Item item) {
        items.remove(item);
    }

    public Item getItem(Long id) {
        for(Item item : items) {
            if(item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Item getItem(int slotIndex) {
        return items.get(slotIndex);
    }

    public void moveItem(int slot1, int slot2) {
        items.set(slot2, items.get(slot1));
        items.remove(slot1);
    }

    public String getItemInfo(int slotIndex) {
        return items.get(slotIndex).getDescription();
    }

    public double getItemValue(int slotIndex) {
        return items.get(slotIndex).getValue();
    }

    public double getInventoryValue() {
        double value = 0;
        for (Item item : items) {
            value += item.getValue();
        }
        return value;
    }
    public int getNextFreeSlotIndex() {
        int index = 0;
        for(Item item : items) {
            if(item != null) {
                index += 1;
            }
            else {
                break;
            }
        }
        return index;
    }
    public Inventory getInventory() {
        return this;
    }
}
