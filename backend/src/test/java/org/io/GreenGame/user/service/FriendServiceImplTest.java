package org.io.GreenGame.user.service;

import org.io.GreenGame.user.model.FriendModel;
import org.io.GreenGame.user.model.FriendsUserModel;
import org.io.GreenGame.user.repository.FriendRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class FriendServiceImplTests {

    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendServiceImpl friendService;

    @Test
    void getAllFriendsByOwnerId() {
        FriendsUserModel friendsUserModel = new FriendsUserModel(1L);
        friendsUserModel.setId(1L);

        FriendModel friendModel = new FriendModel(2L, "gracz1423");
        friendsUserModel.addFriend(friendModel);

        when(friendRepository.findByOwnerId(anyLong())).thenReturn(Optional.of(friendsUserModel));

        Optional<FriendsUserModel> result = friendService.getAllFriendsByOwnerId(1L);

        assertEquals(Optional.of(friendsUserModel), result);
    }
}
