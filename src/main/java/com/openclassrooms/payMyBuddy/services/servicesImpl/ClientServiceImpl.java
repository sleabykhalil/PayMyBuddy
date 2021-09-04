package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;

    @Override
    public Client addNewClient(Client client) {
        Client result=  clientDao.save(client);
        result.setBalance(Balance.builder()
                .amount(0.0)
                .clientId(result.getClientId()).build());
        return clientDao.save(result);
    }
}
