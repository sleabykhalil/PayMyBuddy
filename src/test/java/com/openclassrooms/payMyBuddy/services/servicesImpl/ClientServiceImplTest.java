package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
class ClientServiceImplTest {

    @Autowired
    ClientDao clientDao;

    ClientService clientServiceUnderTest;

    @BeforeEach
    void setUp() {
        clientServiceUnderTest = new ClientServiceImpl(clientDao);
    }

    @Test
    void addClient() {
        Client clientToAdd = Client.builder()
                .emailAccount("khalil@gmail.com")
                .clientPassword("testPassword")
                //.balance(Balance.builder().amount(0.0).build())
                .clientType("Friend")
                .firstName("Khalil")
                .lastName("Sleaby")
                .build();
        Client result = clientServiceUnderTest.addClient(clientToAdd);
        assertThat(result).isEqualTo(clientToAdd);
    }
}