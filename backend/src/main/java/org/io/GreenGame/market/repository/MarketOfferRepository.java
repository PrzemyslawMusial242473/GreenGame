package org.io.GreenGame.market.repository;

import jakarta.persistence.LockModeType;
import org.io.GreenGame.market.model.MarketOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketOfferRepository extends JpaRepository<MarketOffer,Long> {
    @Query("SELECT offer FROM MarketOffer offer WHERE offer.seller.id = :id AND offer.ended = false")
    List<MarketOffer> getUserOffers(Long id);
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT offer FROM MarketOffer  offer WHERE offer.id = :id AND offer.ended = false")
    MarketOffer getOfferByID(Long id);
    @Query("SELECT offer FROM MarketOffer offer WHERE offer.item.name LIKE %:id% AND offer.ended = false ORDER BY offer.item.name ASC")
    List<MarketOffer> getOfferLIKENameAsc(String id);
    @Query("SELECT offer FROM MarketOffer offer WHERE offer.item.name LIKE %:id% AND offer.ended = false ORDER BY offer.item.name DESC")
    List<MarketOffer> getOfferLIKENameDsc(String id);

    @Query("SELECT offer FROM MarketOffer offer WHERE offer.item.name LIKE %:id% AND offer.ended = false ORDER BY offer.price ASC")
    List<MarketOffer> getOfferLIKEPriceAsc(String id);
    @Query("SELECT offer FROM MarketOffer offer WHERE offer.item.name LIKE %:id% AND offer.ended = false ORDER BY offer.price DESC")
    List<MarketOffer> getOfferLIKEPriceDsc(String id);

    @Query("SELECT offer FROM MarketOffer offer WHERE offer.ended = false AND offer.seller.id != :id ORDER BY offer.price ASC")
    List<MarketOffer> getAllOffers(Long id);
}
