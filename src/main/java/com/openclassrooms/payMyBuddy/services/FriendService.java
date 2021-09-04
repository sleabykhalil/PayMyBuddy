package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {

    Friend updateOrInsertFriend(long friendId, Client newFriend);
}
