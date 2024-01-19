package org.io.GreenGame.inventory.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.io.GreenGame.inventory.model.Inventory;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item {

    @ManyToOne
    @JoinColumn(name="fk_inventory")
    private Inventory inventory;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private double value;


}
