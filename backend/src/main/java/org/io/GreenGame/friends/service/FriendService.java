package org.io.GreenGame.friends.service;

import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.model.FriendsUserModel;
import org.io.GreenGame.friends.model.Invitation;
import org.io.GreenGame.user.model.GreenGameUser;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

public interface FriendService {

    Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId);
    Optional<FriendsUserModel> getAllFriendsByOwnerId(Long friendId, String sortBy, String filterBy);
    List<FriendModel> getAllBlockedPeopleByOwnerId(Long friendId);
    public void removeFriend(Long friendId, Long userId);
    public Optional<GreenGameUser> findUserById(Long id);
    public List<FriendModel> getAllUsersOfService();
    // Invitations
    List<Invitation> getPendingInvitations(Long userId);
    boolean sendFriendRequest(Long senderId, Long recipientId);
    void acceptFriendRequest(Long invitationId) throws ChangeSetPersister.NotFoundException;
    void declineFriendRequest(Long invitationId) throws ChangeSetPersister.NotFoundException;
    void blockGamePlayer(Long userId, Long blockedId);
    void unblockGamePlayer(Long userId, Long blockedId);
}
