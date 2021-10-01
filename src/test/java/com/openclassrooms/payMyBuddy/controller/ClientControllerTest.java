package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.NewFriendDto;
import com.openclassrooms.payMyBuddy.model.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClientDao clientDao;

    @Autowired
    BalanceDao balanceDao;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    void setUp() {
        Optional<Client> client;
        client = clientDao.findClientByEmailAccount("newkhalil@gmail.com");
        if (client.isPresent()) {
            balanceDao.deleteById(client.get().getClientId());
            clientDao.deleteById(client.get().getClientId());
        }
        client = clientDao.findClientByEmailAccount("client@gmail.com");
        if (client.isPresent()) {
            clientDao.deleteById(client.get().getClientId());
        }
        client = clientDao.findClientByEmailAccount("friend@gmail.com");
        if (client.isPresent()) {
            clientDao.deleteById(client.get().getClientId());
        }
    }

    @AfterEach
    void afterEach() {
        Optional<Client> client;
        client = clientDao.findClientByEmailAccount("newkhalil@gmail.com");
        if (client.isPresent()) {
            balanceDao.deleteById(client.get().getClientId());
            clientDao.deleteById(client.get().getClientId());
        }
        client = clientDao.findClientByEmailAccount("client@gmail.com");
        if (client.isPresent()) {
            clientDao.deleteById(client.get().getClientId());
        }
        client = clientDao.findClientByEmailAccount("friend@gmail.com");
        if (client.isPresent()) {
            clientDao.deleteById(client.get().getClientId());
        }
    }

    @Test
    void testShowSignUp() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signup"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("clientDto"));
    }

    @Test
    void testSubmitSignUp() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .emailAccount("newkhalil@gmail.com")
                .clientPassword("test")
                .build();

        mockMvc.perform(post("/signup")
                .flashAttr("clientDto", clientDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testSubmitSignUpForSameEmailReturn404() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .emailAccount("newkhalil@gmail.com")
                .clientPassword("test")
                .build();

        mockMvc.perform(post("/signup")
                .flashAttr("clientDto", clientDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        mockMvc.perform(post("/signup")
                .flashAttr("clientDto", clientDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("404"));
    }

    @Test
    @WithMockUser(username = "khalil@gmail.com"
            , password = "123456"
            , roles = {"CLIENT"})
    void addFriendToClient() throws Exception {
        //given
        Client client = Client.builder()
                .emailAccount("client@gmail.com")
                .clientPassword(bCryptPasswordEncoder.encode("test"))
                .build();
        Client friend = Client.builder()
                .emailAccount("friend@gmail.com")
                .clientPassword(bCryptPasswordEncoder.encode("test"))
                .build();
        clientDao.saveAll(List.of(client, friend));
        NewFriendDto newFriendDto = NewFriendDto.builder()
                .newFriendId(clientDao.findClientByEmailAccount(friend.getEmailAccount()).get().getClientId())
                .clientId(clientDao.findClientByEmailAccount(friend.getEmailAccount()).get().getClientId())
                .build();
        //when
        mockMvc.perform(post("/transfer?friend=true")
                .flashAttr("newFriendDto", newFriendDto))
                .andDo(print())
                //then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transfer"));

    }
}