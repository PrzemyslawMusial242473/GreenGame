package org.io.GreenGame.user.repository;

import org.io.GreenGame.user.model.FriendsUserModel;
import org.io.GreenGame.user.model.Invitation;
import org.io.GreenGame.user.model.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findBySenderIdAndStatus(Long senderId, InvitationStatus status);
    List<Invitation> findByRecipientIdAndStatus(Long recipientId, InvitationStatus status);
}
