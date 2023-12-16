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

    @GetMapping("/users/{friendId}/friends")
    public ResponseEntity<FriendsUserModel> getAllFriendsByOwnerId(@PathVariable Long friendId) {
        Optional<FriendsUserModel> friendsList = friendService.getAllFriendsByOwnerId(friendId);

        return friendsList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

}