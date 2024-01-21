package org.io.GreenGame.market.model;

import jakarta.persistence.*;


@Entity
@Table(name = "marketOffer")
public class MarketOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sellerID")
    private MarketUser seller;
    @ManyToOne
    @JoinColumn(name = "buyerID")
    private MarketUser buyer;
    @OneToOne
    @JoinColumn(name = "itemID")
    private MarketItem item;
    @Column(name = "price")
    private double price;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private boolean ended;
    @Version
    private Long version;

    public MarketOffer() {
    }

    public MarketOffer(MarketUser seller, MarketItem item, double price, String description) {
        this.seller = seller;
        this.item = item;
        this.price = price;
        this.description = description;
        this.buyer = null;
        this.ended = false;
    }

    public MarketOffer(Long id, MarketUser seller, MarketUser buyer, MarketItem item, double price, String description, boolean ended) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.item = item;
        this.price = price;
        this.description = description;
        this.ended = ended;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarketUser getSeller() {
        return seller;
    }

    public void setSeller(MarketUser seller) {
        this.seller = seller;
    }

    public MarketUser getBuyer() {
        return buyer;
    }

    public void setBuyer(MarketUser buyer) {
        this.buyer = buyer;
    }

    public MarketItem getItem() {
        return item;
    }

    public void setItem(MarketItem item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "MarketOffer{" +
                "id=" + id +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", item=" + item +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", ended=" + ended +
                '}';
    }
}
