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

    @OneToMany(cascade = CascadeType.ALL)
    private List<ChatUserModel> listOfBlockedUsers = new ArrayList<>();

    private List<ChatUserModel> getAllBlocked() {
        return listOfBlockedUsers;
    }

    public boolean blockUser(ChatUserModel chatUserModel) {
        if (!listOfBlockedUsers.contains(chatUserModel)) {
            listOfBlockedUsers.add(chatUserModel);
            return true;
        }
        else {
            return false;
        }
    }

    public ChatUserModel unblockUser(long id) {
        ChatUserModel userToUnblock = null;
        for (ChatUserModel user : listOfBlockedUsers) {
            if (user.getId() == id) {
                userToUnblock = user;
                break;
            }
        }
        if (userToUnblock != null) {
            listOfBlockedUsers.remove(userToUnblock);
        }
        return userToUnblock;
    }
}