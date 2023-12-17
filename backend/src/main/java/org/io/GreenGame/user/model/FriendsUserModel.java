package org.io.GreenGame.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class FriendsUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long ownerId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FriendModel> friends = new ArrayList<>();


    public FriendsUserModel(long ownerId) {
        this.ownerId = ownerId;
    }

    public FriendsUserModel() {

    }

    public void addFriend(FriendModel friendModel) {
        friends.add(friendModel);
    }

    public FriendModel removeFriend(long id) {
        FriendModel friendToRemove = null;
        for (FriendModel friend : friends) {
            if (friend.getId() == id) {
                friendToRemove = friend;
                break;
            }
        }
        if (friendToRemove != null) {
            friends.remove(friendToRemove);
        }
        return friendToRemove;
    }

    public List<FriendModel> getAllFriends() {
        return friends;
    }
}