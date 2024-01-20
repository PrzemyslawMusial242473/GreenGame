package org.io.GreenGame.market.model;

import jakarta.persistence.*;

@Entity
@Table(name = "marketItem")
public class MarketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "exists")
    private Boolean exists;
    @Column(name = "description")
    private String description;
    @Version
    private Long version;

    public MarketItem() {}

    public MarketItem(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MarketItem(String name, String description) {
        this.name = name;
        this.description = description;
        this.exists = true;
    }

    public MarketItem(String name) {
        this.name = name;
        this.exists = true;
    }

    public MarketItem(String name, Boolean exists) {
        this.name = name;
        this.exists = exists;
    }


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }

    @Override
    public String toString() {
        return "MarketItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exists=" + exists +
                '}';
    }
}
