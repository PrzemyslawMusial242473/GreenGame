package org.io.GreenGame.chat.model;
import jakarta.persistence.*;
import lombok.*;
import org.io.GreenGame.friends.model.FriendModel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}