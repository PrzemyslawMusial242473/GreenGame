package org.io.GreenGame.user.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class FriendModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long whoseUserId;
}
