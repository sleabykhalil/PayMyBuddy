package com.openclassrooms.payMyBuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceControllerTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    @WithMockUser(username = "khalil@gmail.com"
            , password = "123456"
            , roles = {"CLIENT"})
    void feedBalanceFromBankAccount() throws Exception {
        mockMvc.perform(put("/feedBalance")
                .param("clientId", "1")
                .param("amount", "10.00"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "khalil@gmail.com"
            , password = "123456"
            , roles = {"CLIENT"})
    void feedBankAccountFromBalance() throws Exception {
        mockMvc.perform(put("/feedBankAccount")
                .param("clientId", "1")
                .param("amount", "10.00"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}