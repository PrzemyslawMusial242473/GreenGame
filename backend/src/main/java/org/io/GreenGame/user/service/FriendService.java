package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;
import org.io.GreenGame.user.model.Invitation;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId);
    Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId, String sortBy, String filterBy);
    public void removeFriend(Long friendId, Long userId);

    // Invitations
    List<Invitation> getPendingInvitations(Long userId);
    void sendFriendRequest(Long senderId, Long recipientId);
    void acceptFriendRequest(Long invitationId);
    void declineFriendRequest(Long invitationId);

    // Observers (TODO - czy to na pewno dobry sposób?)
    void addObserver(Long userId, FriendInvitationObserver observer);
    void removeObserver(Long userId, FriendInvitationObserver observer);
    void notifyObservers(Long userId);
}
