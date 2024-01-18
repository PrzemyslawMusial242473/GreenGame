package org.io.GreenGame.friends.repository;

import org.io.GreenGame.friends.model.Invitation;
import org.io.GreenGame.friends.model.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findBySenderIdAndStatus(Long senderId, InvitationStatus status);
    List<Invitation> findByRecipientIdAndStatus(Long recipientId, InvitationStatus status);
}
