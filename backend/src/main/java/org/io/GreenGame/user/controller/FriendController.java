package org.io.GreenGame.user.controller;
import org.io.GreenGame.user.model.FriendsUserModel;
import org.io.GreenGame.user.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/users/get/{friendId}")
    public ResponseEntity<FriendsUserModel> getAllFriendsByOwnerId(
            @PathVariable Long friendId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String filterBy) {

        Optional<FriendsUserModel> friendsList = friendService.getAllFriendsByOwnerId(friendId, sortBy, filterBy);

        return friendsList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/users/delete/{userId}/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        try {
            System.out.println("test");
            friendService.removeFriend(friendId, userId);
            return ResponseEntity.ok("Friend removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting friend.");
        }
    }

    @GetMapping("/get/invitation/all/{userId}")
    public ResponseEntity<?> getAllInvitations(@RequestParam Long senderId, @RequestParam Long recipientId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

    @GetMapping("/send-invitation/{userId}/{receiverId}")
    public ResponseEntity<?> sendInvitation(@RequestParam Long senderId, @RequestParam Long recipientId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

    @GetMapping("/accept-invitation/{userId}/{receiverId}")
    public ResponseEntity<?> acceptInvitation(@RequestParam Long invitationId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

    @GetMapping("/decline-invitation/{userId}/{receiverId}")
    public ResponseEntity<?> declineInvitation(@RequestParam Long invitationId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
    }

}