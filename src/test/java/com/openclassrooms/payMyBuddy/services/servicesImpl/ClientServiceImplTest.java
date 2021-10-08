package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    ClientDao clientDaoMock;

    @Mock
    ClientMapper clientMapperMock;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoderMock;

    ClientService clientServiceUnderTest;

    @BeforeEach
    void setUp() {
        clientServiceUnderTest = new ClientServiceImpl(clientDaoMock,  clientMapperMock,  bCryptPasswordEncoderMock);
    }

    @Test
    void addNewClient_WhenClientEmailNotExist_AddClient() {
        //given
        Client client = Client.builder()
                .firstName("khalil")
                .emailAccount("khalil@gmail.com").build();
        when(clientDaoMock.findClientByEmailAccount(client.getEmailAccount())).thenReturn(Optional.empty());
        when(clientDaoMock.save(client)).thenReturn(client);

        //when
        clientServiceUnderTest.addNewClient(client);

        //then
        verify(clientDaoMock, times(2)).save(client);
    }
}