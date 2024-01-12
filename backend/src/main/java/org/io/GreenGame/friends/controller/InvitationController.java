package org.io.GreenGame.friends.controller;

import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.model.Invitation;
import org.io.GreenGame.friends.model.InvitationStatus;
import org.io.GreenGame.friends.service.FriendService;
import org.io.GreenGame.user.model.GreenGameUser;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok(pendingInvitations);
    }

    @GetMapping("/send/{senderId}/{recipientId}")
    public ResponseEntity<?> sendInvitation(@PathVariable Long recipientId) {
        try {
            Optional<GreenGameUser> friend = friendService.findUserById(recipientId);
            if (friend.isPresent()) {
                friendService.sendFriendRequest(getIdOfLoggedUser(), recipientId);
                return ResponseEntity.ok("Invitation sent successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No user of that ID.");
            }
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
