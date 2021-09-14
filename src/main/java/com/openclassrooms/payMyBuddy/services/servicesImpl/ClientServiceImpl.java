package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.Friend;
import com.openclassrooms.payMyBuddy.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    @Override
    public Client addNewClient(Client client) {
        if (clientDao.findClientByEmailAccount(client.getEmailAccount()).isEmpty()) {
            Client result = clientDao.save(client);
            result.setBalance(Balance.builder()
                    .amount(0.0)
                    .clientId(result.getClientId()).build());
            return clientDao.save(result);
        } else {
            log.info("This Email account[{}] is already used," , client.getEmailAccount());
            return null;
        }
    }
    @Override
    public Client updateOrInsertFriend(String friendEmailAccount, long clientId) {
        Optional<Client> friend = clientDao.findClientByEmailAccount(friendEmailAccount);
        Optional<Client> client = clientDao.findById(clientId);
        if (client.isPresent()) {
            if (friend.isPresent()) {
                client.get().getFriends().add(Friend.builder().friendId(friend.get().getClientId()).build());
                return clientDao.save(client.get());

            } else {
                log.error("Client with email account 5[{}] not found", friendEmailAccount);
            }

        } else {
            log.error(" can not find client in DB ");
        }
        return null;
    }
}

