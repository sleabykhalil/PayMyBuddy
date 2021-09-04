package com.openclassrooms.payMyBuddy.services.servicesImpl.integration;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dao.FriendDao;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import com.openclassrooms.payMyBuddy.services.FriendService;
import com.openclassrooms.payMyBuddy.services.servicesImpl.FriendServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FriendServiceImplIT {

    @Autowired
    FriendDao friendDao;

    @Autowired
    ClientDao clientDao;

    FriendService friendServiceUnderTest;

    @BeforeEach
    void setUp() {
    friendServiceUnderTest=new FriendServiceImpl(friendDao);
    }

    @Test
    void updateOrInsertFriend_WhenFriendNotExist_NewFriendAdded() {
        Client client1= Client.builder()
                .firstName("Khalil")
                .lastName("sleaby")
                .clientPassword("password")
                .clientType("Friend")
                .emailAccount("khalil@gmail.com")
                .build();
        Client client2= Client.builder()
                .firstName("Aram")
                .lastName("sleaby")
                .clientPassword("password")
                .clientType("Friend")
                .emailAccount("Aram@gmail.com")
                .build();
        client1 = clientDao.save(client1);
        client2 = clientDao.save(client2);

        Friend result = friendServiceUnderTest.updateOrInsertFriend(client1.getClientId(),client2);
        assertThat(result.getClients().size()).isEqualTo(1);
    }

    @Test
    void updateOrInsertFriend_WhenFriendExist_FriendUpdated() {
        Client client1= Client.builder()
                .firstName("Khalil")
                .lastName("sleaby")
                .clientPassword("password")
                .clientType("Friend")
                .emailAccount("khalil@gmail.com")
                .build();
        Client client2= Client.builder()
                .firstName("Aram")
                .lastName("sleaby")
                .clientPassword("password")
                .clientType("Friend")
                .emailAccount("Aram@gmail.com")
                .build();
        client1 = clientDao.save(client1);
        client2 = clientDao.save(client2);
        friendDao.save(Friend.builder()
                .friendId(10)
                .Clients(List.of(client1)).build());
        Optional<Friend> resultBefore=friendDao.findById(10L);
        assertThat(resultBefore.get().getFriendId()).isEqualTo(10L);
        assertThat(resultBefore.get().getClients().size()).isEqualTo(1);


        Friend result = friendServiceUnderTest.updateOrInsertFriend(10L,client2);
        assertThat(result.getFriendId()).isEqualTo(10);
        assertThat(result.getClients().size()).isEqualTo(2);
    }
}