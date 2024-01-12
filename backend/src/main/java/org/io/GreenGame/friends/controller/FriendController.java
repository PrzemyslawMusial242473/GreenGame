package org.io.GreenGame.friends.controller;
import org.io.GreenGame.friends.model.FriendModel;
import org.io.GreenGame.friends.model.FriendsUserModel;
import org.io.GreenGame.friends.service.FriendService;
import org.io.GreenGame.user.service.implementation.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.io.GreenGame.friends.RestClientConfig;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/secured/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    @GetMapping("/users/get")
    public ResponseEntity<FriendsUserModel> getAllFriendsByOwnerId(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String filterBy) {

        Optional<FriendsUserModel> friendsList = friendService.getAllFriendsByOwnerId(getIdOfLoggedUser(), sortBy, filterBy);

        return friendsList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/users/delete/{friendId}")
    public ResponseEntity<String> removeFriend(
            @PathVariable Long friendId) {
        try {
            System.out.println("test");
            friendService.removeFriend(friendId, getIdOfLoggedUser());
            return ResponseEntity.ok("Friend removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting friend.");
        }
    }

    @GetMapping("/users/get/allusers")
    public ResponseEntity<List<FriendModel>> getAllUsers() {
        List<FriendModel> users = friendService.getAllUsersOfService();
        return ResponseEntity.ok(users);
    }

    private Long getIdOfLoggedUser(){
        return authServiceImplementation.getUserFromSession().getId();
    }
}