package org.io.GreenGame.inventory.repository;

import org.io.GreenGame.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT COUNT(item.id) FROM Item item WHERE item.id = :id")
    Long checkItemIDInDatabase(Long id);

    @Query("SELECT COUNT(item.id) FROM Item item WHERE item.name = :name")
    Long checkItemNameInDatabase(String name);

    @Query("SELECT item FROM Item item WHERE item.id = :id")
    Item findItemByID(Long id);
}
