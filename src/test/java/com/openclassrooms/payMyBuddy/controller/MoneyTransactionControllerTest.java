package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.dao.MoneyTransactionDao;
import com.openclassrooms.payMyBuddy.dto.MoneyTransactionDto;
import com.openclassrooms.payMyBuddy.model.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

import java.util.List;

import static com.openclassrooms.payMyBuddy.services.servicesImpl.MoneyTransactionServiceImpl.FEE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureCache
//@DataJpaTest

class MoneyTransactionControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BalanceDao balanceDao;
    @Autowired
    MoneyTransactionDao moneyTransactionDao;

    @BeforeEach
    void setUp() {

    }

    @Test
    @WithMockUser(username = "khalil@gmail.com"
            , password = "123456"
            , roles = {"CLIENT"})
    void sendMoney() throws Exception {
        //given
        MoneyTransactionDto moneyTransactionDto = MoneyTransactionDto.builder()
                .senderClientId(1)
                .receiverClientId(2)
                .amount(10.0)
                .build();
        Balance senderBalance = balanceDao.getById(1L);
        Balance receiverBalance = balanceDao.getById(2L);
        double receiverBalanceAmountBefore = senderBalance.getAmount();
        double senderBalanceAmountBefore = senderBalance.getAmount();
        receiverBalanceAmountBefore = receiverBalance.getAmount();
        senderBalance.setAmount(senderBalanceAmountBefore + 10.0);
        balanceDao.save(senderBalance);
        //when
        mockMvc.perform(post("/transfer?transaction=true")
                .flashAttr("moneyTransactionDto", moneyTransactionDto))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transfer"));

        //then
        senderBalance = balanceDao.getById(1L);
        receiverBalance = balanceDao.getById(2L);
        assertThat(senderBalance.getAmount()).isEqualTo(senderBalanceAmountBefore - 10 * FEE);
        assertThat(receiverBalance.getAmount()).isEqualTo(receiverBalanceAmountBefore + 10.0);

        senderBalance.setAmount(senderBalanceAmountBefore);
        receiverBalance.setAmount(receiverBalanceAmountBefore);
        balanceDao.saveAll(List.of(senderBalance, receiverBalance));
    }

    @Test
    @WithMockUser(username = "khalil@gmail.com"
            , password = "123456"
            , roles = {"CLIENT"})
    void getTransactionList() throws Exception {
        mockMvc.perform(get("/transfer"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("transfer"))
                .andExpect(model().size(8))
                .andExpect(model().attributeExists("clientDto"))
                .andExpect(model().attributeExists("notFriendList"))
                .andExpect(model().attributeExists("friendsEmailList"))
                .andExpect(model().attributeExists("moneyTransactionDto"))
                .andExpect(model().attributeExists("friendsEmailList"))
                .andExpect(model().attributeExists("moneyTransactionDto"))
                .andExpect(model().attributeExists("paymentDtoList"))
                .andExpect(model().attributeExists("numberOfTransaction"))
                .andExpect(model().attributeExists("pageNumberList"));
    }
}