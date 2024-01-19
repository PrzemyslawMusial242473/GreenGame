package org.io.GreenGame.inventory.repository;

import org.io.GreenGame.inventory.model.Inventory;
import org.io.GreenGame.inventory.model.Item;
import org.io.GreenGame.user.model.GreenGameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT COUNT(inventory.id) FROM Inventory inventory WHERE inventory.id = :id")
    Long checkInventoryIDInDatabase(Long id);

    /*TODO: Po zmianie w Inventory (z user_id na GreenGameUser) ustawić:
     * @Query("SELECT COUNT(inventory.id) FROM Inventory inventory WHERE inventory.user.id = :userID")
     */
    @Query("SELECT COUNT(inventory.id) FROM Inventory inventory WHERE inventory.userId = :userID")
    Long checkInventoryIDByUserID(Long userID);

    @Query("SELECT inventory FROM Inventory inventory WHERE inventory.id = :id")
    Inventory findInventoryByID(Long id);


    /*TODO: Po zmianie w Inventory (z user_id na GreenGameUser) ustawić:
     * @Query("SELECT inventory FROM Inventory inventory WHERE inventory.user.id = :userID")
     */
    @Query("SELECT inventory FROM Inventory inventory WHERE inventory.userId = :userID")
    Inventory findInventoryByUserID(Long userID);

    @Query("SELECT inventory FROM Inventory inventory")
    Inventory selectAll();


}
