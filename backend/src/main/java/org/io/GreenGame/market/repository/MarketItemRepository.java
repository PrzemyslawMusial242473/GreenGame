package org.io.GreenGame.market.repository;

import jakarta.persistence.LockModeType;
import org.io.GreenGame.market.model.MarketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketItemRepository extends JpaRepository<MarketItem,Long> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT item FROM MarketItem item WHERE item.id = :id")
    MarketItem getMarketItemByID(Long id);

}
