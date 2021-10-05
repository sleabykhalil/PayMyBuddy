package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {
    Client addNewClient(Client client);

    Client updateOrInsertFriend(String friendEmailAccount, long clientId);

    Client findClientByEmail(String clientEmail);

    List<Client> findAllFriends(List<Friend> friends);

    List<Client> findNotFriendList(Client client);

    Client findClientById(long clientId);
}
