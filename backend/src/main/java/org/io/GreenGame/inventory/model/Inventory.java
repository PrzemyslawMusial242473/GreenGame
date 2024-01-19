package org.io.GreenGame.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.io.GreenGame.user.model.GreenGameUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="inventories")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;


    /* TODO:
        "odkomentować" i  podobnie zrobić w GreenGameUser
    *   ⬇️
    * */
//    @OneToOne
//    @JoinColumn(name="fk_user")
//    @JsonBackReference
//    private GreenGameUser user;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    //money money money
    @Column(name="balance")
    private double balance;


    //add item to next free slot
    public void addItem(Item item) {
//        addItem(getNextFreeSlotIndex(), item);
        items.add(item);
        item.setInventory(this);
    }

    //add item to specific slot
    public void addItem(int slotIndex, Item item) {
        items.set(slotIndex, item);
        item.setInventory(this);
    }

    //Delete by slot id
    public void deleteItem(int slotIndex) {
        items.set(slotIndex, null);
//        items.remove(slotIndex);
    }

    //Delete by item
    public void deleteItem(Item item) {
        for(Item itemI: items) {
            if(Objects.equals(itemI.getId(), item.getId())) {
                System.out.println("Item found");
//                items.set(items.indexOf(itemI), null);
                items.remove(itemI);
                item.setInventory(null);
                break;
            }
        }
    }

    public Item getItem(Long id) {
        for(Item item : items) {
            if(Objects.equals(item.getId(), id)) {
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
        if (items.get(slotIndex) != null) {
            return items.get(slotIndex).getValue();
        } else {
            return 0.0; // lub inna wartość domyślna dla przypadku, gdy przedmiot jest null
        }
    }

    public double getInventoryValue() {
        double value = 0;
        for (Item item : items) {
            if (item != null) {
                value += item.getValue();
            }
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

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", userId=" + userId +
                ", items=" + items +
                ", balance=" + balance +
                '}';
    }
}
