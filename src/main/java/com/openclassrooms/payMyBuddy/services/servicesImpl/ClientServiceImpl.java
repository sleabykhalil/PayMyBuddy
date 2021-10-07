package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    ClientMapper clientMapper;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Add New client
     *
     * @param client
     * @return
     */
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

    /**
     * Add or update friend list
     *
     * @param friendEmailAccount
     * @param clientId
     * @return
     */
    @Override
    public Client updateOrInsertFriend(String friendEmailAccount, long clientId) {
        Optional<Client> friendAsClient = clientDao.findClientByEmailAccount(friendEmailAccount);
        Optional<Client> client = clientDao.findById(clientId);
        if (client.isPresent()) {
            if (friendAsClient.isPresent()) {
                ClientDto clientDto = clientMapper.clientToClientDto(client.get());
                ClientDto friendAsClientDto = clientMapper.clientToClientDto(friendAsClient.get());
                Client friendToAdd;
                ClientDto friendToAddDto = null;
                if (clientDao.existsById(friendAsClient.get().getClientId())) {
                    friendToAdd = clientDao.getById(friendAsClient.get().getClientId());
                    friendToAddDto = clientMapper.clientToClientDto(friendToAdd);
                    friendToAddDto.getFriends().add(client.get());
                    clientDto.getFriends().add(clientMapper.clientDtoToClient(friendToAddDto));

                } else {
                    log.error("Account not Exist");
                }

                return clientDao.save(clientMapper.clientDtoToClient(clientDto));

            } else {
                log.error("Client with email account =[{}] not found", friendEmailAccount);
            }

        } else {
            log.error(" can not find client in DB ");
        }
        return null;
    }

    /**
     * Find client By Email Account
     *
     * @param clientEmail
     * @return
     */
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

    /**
     * get all Friend for client
     *
     * @param friends
     * @return
     */
    @Override
    public List<Client> findAllFriends(List<Client> friends) {
        List<Client> friendList = clientDao.findAllById(friends
                .stream().map(Client::getClientId).collect(Collectors.toList()));
        return friendList;
    }

    /**
     * Get list of clients not a friend to client
     *
     * @param client
     * @return
     */
    @Override
    public List<Client> findNotFriendList(Client client) {
        List<Client> notFriendList = clientDao.findAll();

        List<Long> friendId = getFriendIdList(client.getFriends());

        List<Client> friendList = new ArrayList<>();


        friendList.add(client);
        friendList.addAll(clientDao.findAllById(friendId));
        notFriendList.removeAll(friendList);
        return notFriendList;
    }

    @Override
    public Client findClientById(long clientId) {
        return clientDao.getById(clientId);
    }

    private List<Long> getFriendIdList(List<Client> friends) {
        return friends.stream()
                .map(Client::getClientId)
                .collect(Collectors.toList());
    }
}

