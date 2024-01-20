package org.io.GreenGame.market.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "marketUser")
public class MarketUser {
    @Id
    private Long id;
    @Transient
    private List<MarketItem> inv;
    @Column(name = "money")
    private double money;
    @Version
    private Long version;

    public MarketUser() {}

    public MarketUser(Long id, List<MarketItem> inv, double money) {
        this.id = id;
        this.inv = inv;
        this.money = money;
    }

    public MarketUser(Long id) {
        this.id = id;
        this.money = 0;
    }

    public MarketUser(List<MarketItem> inv, double money) {
        this.inv = inv;
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MarketItem> getInv() {
        return inv;
    }

    public void setInv(List<MarketItem> inv) {
        this.inv = inv;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "MarketUser{" +
                "id=" + id +
                ", inv=" + inv +
                ", money=" + money +
                '}';
    }
}
