package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.model.Client;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    Client addNewClient(Client client);

    Client updateOrInsertFriend(String friendEmailAccount, long clientId);
}
