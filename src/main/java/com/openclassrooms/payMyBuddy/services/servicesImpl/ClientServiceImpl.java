package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dao.FriendDao;
import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.FriendDto;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.dto.mapper.FriendMapper;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import com.openclassrooms.payMyBuddy.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientDao clientDao;
    @Autowired
    private final FriendDao friendDao;
    @Autowired
    ClientMapper clientMapper;
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Client addNewClient(Client client) {
        if (clientDao.findClientByEmailAccount(client.getEmailAccount()).isEmpty()) {
            client.setClientPassword(bCryptPasswordEncoder.encode(client.getClientPassword()));
            Client result = clientDao.save(client);
            result.setBalance(Balance.builder()
                    .amount(0.0)
                    .clientId(result.getClientId()).build());
            return clientDao.save(result);
        } else {
            log.info("This Email account[{}] is already used,", client.getEmailAccount());
            return null;
        }
    }

    @Override
    public Client updateOrInsertFriend(String friendEmailAccount, long clientId) {
        Optional<Client> friendAsClient = clientDao.findClientByEmailAccount(friendEmailAccount);
        Optional<Client> client = clientDao.findById(clientId);
        if (client.isPresent()) {
            if (friendAsClient.isPresent()) {
                ClientDto clientDto = clientMapper.clientToClientDto(client.get());
                ClientDto friendAsClientDto = clientMapper.clientToClientDto(friendAsClient.get());
                Friend friendToAdd;
                FriendDto friendToAddDto;
                if (friendDao.existsById(friendAsClient.get().getClientId())) {
                    friendToAdd = friendDao.getById(friendAsClient.get().getClientId());
                    friendToAddDto = friendMapper.friendToFriendDto(friendToAdd);
                    friendToAddDto.getClients().add(client.get());
                } else {
                    friendToAddDto = FriendDto.builder()
                            .friendId(friendAsClient.get().getClientId())
                            .Clients(List.of(client.get()))
                            .build();
//                    friendDao.save(friendToAdd);
//                    client.get().getFriends().add(friendToAdd);
                }
                clientDto.getFriends().add(friendMapper.friendDtoToFriend(friendToAddDto));

                return clientDao.save(clientMapper.clientDtoToClient(clientDto));

            } else {
                log.error("Client with email account =[{}] not found", friendEmailAccount);
            }

        } else {
            log.error(" can not find client in DB ");
        }
        return null;
    }

    @Override
    public Client findClientByEmail(String clientEmail) {
        Optional<Client> client = clientDao.findClientByEmailAccount(clientEmail);
        if (client.isPresent()) {
            return client.get();
        } else {
            log.error("Client with Email =[{}] not found", clientEmail);
            return null;
        }
    }

    @Override
    public List<Client> findAllFriends(List<Friend> friends) {
        List<Client> friendList = clientDao.findAllById(friends
                .stream().map(Friend::getFriendId).collect(Collectors.toList()));
        return friendList;
    }

    @Override
    public List<Client> findNotFriendList(Client client) {
        List<Client> notFriendList = clientDao.findAll();

        List<Long> friendId = getFriendIdList(client.getFriends());

        List<Client> friendList = clientDao.findAllById(friendId);
        friendList.add(client);
        notFriendList.removeAll(friendList);
        return notFriendList;
    }

    @Override
    public Client findClientById(long clientId) {
        return clientDao.getById(clientId);
    }

    private List<Long> getFriendIdList(List<Friend> friends) {
        return friends.stream()
                .map(Friend::getFriendId)
                .collect(Collectors.toList());
    }
}

