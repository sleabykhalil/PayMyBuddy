package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
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
            log.info("Email must be unique");
            return null;
        }
    }
}
