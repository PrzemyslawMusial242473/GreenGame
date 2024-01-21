package org.io.GreenGame.market.repository;

import jakarta.persistence.LockModeType;
import org.io.GreenGame.market.model.MarketUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketUserRepository extends JpaRepository<MarketUser,Long> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT user FROM MarketUser user WHERE user.id = :id")
    MarketUser getMarketUserByID(Long id);
}
