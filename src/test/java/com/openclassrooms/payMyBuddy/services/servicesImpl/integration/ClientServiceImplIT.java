package com.openclassrooms.payMyBuddy.services.servicesImpl.integration;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapperImpl;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import com.openclassrooms.payMyBuddy.services.servicesImpl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
//@SpringBootTest
class ClientServiceImplIT {

    @Autowired
    ClientDao clientDao;


    ClientMapper clientMapper;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    ClientService clientServiceUnderTest;

    @BeforeEach
    void setUp() {
        clientMapper = new ClientMapperImpl();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        clientServiceUnderTest = new ClientServiceImpl(clientDao,  clientMapper,  bCryptPasswordEncoder);
    }

    @Test
    void addNewClient() {
        Client clientToAdd = Client.builder()
                .emailAccount("testkhalil@gmail.com")
                .clientPassword("testPassword")
                .clientType("Friend")
                .firstName("Khalil")
                .lastName("Sleaby")
                .build();
        Client result = clientServiceUnderTest.addNewClient(clientToAdd);

        assertThat(result).isEqualTo(clientToAdd);
    }
}