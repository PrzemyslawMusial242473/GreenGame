package org.io.GreenGame.friends.controller;

import org.io.GreenGame.friends.model.Invitation;
import org.io.GreenGame.friends.model.InvitationStatus;
import org.io.GreenGame.friends.service.FriendService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/secured/api/friends/invitations")
public class InvitationController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    @GetMapping("/pending")
    public ResponseEntity<List<Invitation>> getPendingInvitations() {
        List<Invitation> pendingInvitations = friendService.getPendingInvitations(getIdOfLoggedUser());
        List<Invitation> test = new ArrayList<>();
        test.add(new Invitation(100L, 100L, InvitationStatus.PENDING));
        return ResponseEntity.ok(test);
    }

    @GetMapping("/send/{senderId}/{recipientId}")
    public ResponseEntity<?> sendInvitation(@PathVariable Long recipientId) {
        try {
            friendService.sendFriendRequest(getIdOfLoggedUser(), recipientId);
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

    private Long getIdOfLoggedUser(){
        return authServiceImplementation.getUserFromSession().getId();
    }
}
