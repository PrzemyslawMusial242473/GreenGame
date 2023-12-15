package org.io.GreenGame.user.controller;
import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/{userId}")
    public List<FriendModel> getAllFriends(@PathVariable Long userId) {
        return null;
        //friendService.getAllFriends(userId);
    }

    @GetMapping("/{userId}/{friendId}")
    public Optional<FriendModel> getFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return friendService.getFriendById(friendId);
    }

    @PostMapping("/{userId}")
    public void addFriend(@PathVariable Long userId, @RequestBody FriendModel friend) {
        friendService.saveFriend(userId, friend);
    }

    @PutMapping("/{friendId}")
    public void updateFriend(@PathVariable Long friendId, @RequestBody FriendModel friend) {
        friendService.updateFriend(friendId, friend);
    }

    @DeleteMapping("/{friendId}")
    public void deleteFriend(@PathVariable Long friendId) {
        friendService.deleteFriend(friendId);
    }
}
