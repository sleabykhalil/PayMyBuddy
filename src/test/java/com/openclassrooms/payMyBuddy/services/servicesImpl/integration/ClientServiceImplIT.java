package com.openclassrooms.payMyBuddy.services.servicesImpl.integration;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import com.openclassrooms.payMyBuddy.services.servicesImpl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
class ClientServiceImplIT {

    @Autowired
    ClientDao clientDao;

    ClientService clientServiceUnderTest;

    @BeforeEach
    void setUp() {
        clientServiceUnderTest = new ClientServiceImpl(clientDao);
    }

    @Test
    void addNewClient() {
        Client clientToAdd = Client.builder()
                .emailAccount("khalil@gmail.com")
                .clientPassword("testPassword")
                //.balance(Balance.builder().amount(0.0).build())
                .clientType("Friend")
                .firstName("Khalil")
                .lastName("Sleaby")
                .build();
        Client result = clientServiceUnderTest.addNewClient(clientToAdd);
        assertThat(result).isEqualTo(clientToAdd);
    }
}