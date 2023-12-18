package org.io.GreenGame.user.controller;

import org.io.GreenGame.user.model.Invitation;
import org.io.GreenGame.user.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends/invitations")
public class InvitationController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<Invitation>> getPendingInvitations(@PathVariable Long userId) {
        List<Invitation> pendingInvitations = friendService.getPendingInvitations(userId);
        return ResponseEntity.ok(pendingInvitations);
    }

    @GetMapping("/send/{senderId}/{recipientId}")
    public ResponseEntity<?> sendInvitation(@PathVariable Long senderId, @PathVariable Long recipientId) {
        try {
            friendService.sendFriendRequest(senderId, recipientId);
            return ResponseEntity.ok("Invitation sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending invitation.");
        }
    }

    @GetMapping("/accept/{invitationId}")
    public ResponseEntity<?> acceptInvitation(@PathVariable Long invitationId) {
        try {
            friendService.acceptFriendRequest(invitationId);
            return ResponseEntity.ok("Invitation accepted successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitation not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting invitation.");
        }
    }

    @GetMapping("/decline/{invitationId}")
    public ResponseEntity<?> declineInvitation(@PathVariable Long invitationId) {
        try {
            friendService.declineFriendRequest(invitationId);
            return ResponseEntity.ok("Invitation declined successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitation not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error declining invitation.");
        }
    }
}
