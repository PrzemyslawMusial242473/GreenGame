package org.io.GreenGame.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class FriendsUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long ownerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ownerId")  // Specify the foreign key column
    private List<FriendModel> friends = new ArrayList<>();


    public FriendsUserModel(long ownerId) {
        this.ownerId = ownerId;
    }

    public FriendsUserModel() {

    }

    public List<FriendModel> addFriend(FriendModel friendModel) {
        friends.add(friendModel);
        return friends;
    }
}