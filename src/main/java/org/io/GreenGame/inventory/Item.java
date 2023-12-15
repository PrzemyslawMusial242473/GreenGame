package org.io.GreenGame.inventory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Items")
public class Item {

    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private Inventory inventory;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private double value;

}
