package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.FriendDao;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import com.openclassrooms.payMyBuddy.services.FriendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendServiceImplTest {

    @Mock
    FriendDao friendDaoMock;

    FriendService friendServiceUnderTest;

    @BeforeEach
    void setUp() {
        friendServiceUnderTest = new FriendServiceImpl(friendDaoMock);
    }

    @Test
    void updateOrInsertFriend_whenFriendNotExist_addNewFriend() {
        //given
        long friendId = 1L;
        Client client = Client.builder()
                .firstName("khalil")
                .lastName("sleaby").build();
        Friend newFriend = Friend.builder()
                .friendId(friendId)
                .Clients(List.of(client))
                .build();
        when(friendDaoMock.findById(friendId)).thenReturn(Optional.empty());
        when(friendDaoMock.save(newFriend)).thenReturn(newFriend);
        //when
        Friend result = friendServiceUnderTest.updateOrInsertFriend(friendId, client);
        //then
        assertThat(result).isEqualTo(newFriend);
    }

    @Test
    void updateOrInsertFriend_whenFriendExist_FriendUpdate() {
        //given
        long friendId = 1L;
        Client client = Client.builder()
                .firstName("khalil")
                .lastName("sleaby").build();
        Friend friendBefore = Friend.builder()
                .friendId(friendId)
                .Clients(new ArrayList<>())
                .build();
        when(friendDaoMock.findById(friendId)).thenReturn(Optional.ofNullable(friendBefore));
        Friend friendAfter = Friend.builder()
                .friendId(friendId)
                .Clients(List.of(client)).build();
        when(friendDaoMock.save(friendAfter)).thenReturn(friendAfter);
        //when
        Friend result = friendServiceUnderTest.updateOrInsertFriend(friendId, client);
        //then
        assertThat(result).isEqualTo(friendAfter);
    }

}