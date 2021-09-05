package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.FriendDao;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import com.openclassrooms.payMyBuddy.services.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {
    private final FriendDao friendDao;

    @Override
    public Friend updateOrInsertFriend(long friendId, Client newFriend) {

        Optional<Friend> clientToAddFriends = friendDao.findById(friendId);
        if (clientToAddFriends.isEmpty()){
            //add new friend
            return friendDao.save(Friend.builder()
                    .friendId(friendId)
                    .Clients(List.of(newFriend)).build());

        }else{
            //update Existed friend
            clientToAddFriends.get().getClients().add(newFriend);
            return friendDao.save(clientToAddFriends.get());
        }


    }
}
