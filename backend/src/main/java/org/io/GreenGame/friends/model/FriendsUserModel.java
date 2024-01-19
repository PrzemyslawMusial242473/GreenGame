package org.io.GreenGame.friends.model;

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
    @OneToMany(cascade = CascadeType.ALL)
    private List<FriendModel> blockedUsers = new ArrayList<>();


    public FriendsUserModel(long ownerId) {
        this.ownerId = ownerId;
    }

    public FriendsUserModel() {

    }

    public boolean addFriend(FriendModel friendModel) {
        if (!blockedUsers.contains(friendModel)) {
            friends.add(friendModel);
            return true;
        }
        else {
            return false;
        }
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

    public FriendModel blockUser(FriendModel userToBeBlocked) {
        if (!blockedUsers.contains(userToBeBlocked)) {
            blockedUsers.add(userToBeBlocked); // checking so we don't add twice
        }
        removeFriend(userToBeBlocked.getId());
        return userToBeBlocked;
    }

    public FriendModel unblockUser(FriendModel userToBeUnblocked) {
        blockedUsers.remove(userToBeUnblocked);
        return userToBeUnblocked;
    }

    public List<FriendModel> getAllFriends() {
        return friends;
    }
    public List<FriendModel> getAllBlockedUsers() {
        return blockedUsers;
    }
}