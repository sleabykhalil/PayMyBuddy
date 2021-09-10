package com.openclassrooms.payMyBuddy.services.servicesImpl.integration;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dao.MoneyTransactionDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.model.Payment;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import com.openclassrooms.payMyBuddy.services.servicesImpl.MoneyTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MoneyTransactionServiceImplIT {

    @Autowired
    BalanceDao balanceDao;
    @Autowired
    MoneyTransactionDao moneyTransactionDao;
    @Autowired
    ClientDao clientDao;

    MoneyTransactionService moneyTransactionServiceUnderTest;

    @BeforeEach
    void setUp() {
        moneyTransactionServiceUnderTest = new MoneyTransactionServiceImpl(moneyTransactionDao, balanceDao);
    }

    @Test
    void sendMoney() {
        //given
        Client client;
        Client friend;
        client = Client.builder()
                .clientId(1L)
                .emailAccount("khalil1@gmail.com")
                .clientPassword("Password")
                .build();
        friend = Client.builder()
                .clientId(2L)
                .emailAccount("khalil2@gmail.com")
                .clientPassword("Password")
                .build();
        client = clientDao.save(client);
        friend = clientDao.save(friend);
        client.setBalance(Balance.builder().clientId(client.getClientId()).amount(10.0).build());
        friend.setBalance(Balance.builder().clientId(friend.getClientId()).amount(10.0).build());
        client = clientDao.save(client);
        friend = clientDao.save(friend);

        MoneyTransaction moneyTransaction = MoneyTransaction.builder()
                .senderClientId(client.getClientId())
                .receiverClientId(friend.getClientId())
                .payment(Payment.builder().amount(5.00).build()).build();
        //when
        MoneyTransaction result = moneyTransactionServiceUnderTest.sendMoney(moneyTransaction);
        client = clientDao.getById(client.getClientId());
        friend = clientDao.getById(friend.getClientId());

        //then
        assertThat(result.getPayment().getTransactionId()).isEqualTo(result.getId());
        assertThat(client.getBalance().getAmount()).isEqualTo(4.975);
        assertThat(friend.getBalance().getAmount()).isEqualTo(15.0);
        assertThat(result.getPayment().getFee()).isEqualTo(0.025);
    }
}