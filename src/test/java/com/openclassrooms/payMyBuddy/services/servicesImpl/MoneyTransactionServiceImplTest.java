package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.dao.ClientDao;
import com.openclassrooms.payMyBuddy.dao.MoneyTransactionDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.model.Payment;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoneyTransactionServiceImplTest {
    @Mock
    BalanceDao balanceDaoMock;
    @Mock
    MoneyTransactionDao moneyTransactionDaoMock;
    //@Mock
    //ClientDao clientDaoMock;

    MoneyTransactionService moneyTransactionServiceUnderTest;

    @BeforeEach
    void setUp() {
        moneyTransactionServiceUnderTest = new MoneyTransactionServiceImpl(moneyTransactionDaoMock, balanceDaoMock);
    }

    @Test
    void sendMoney_WhenAmountEnough_SaveMoneyTransaction() {
        //given
        Client client;

        client = Client.builder()
                .clientId(1L).build();
        MoneyTransaction moneyTransaction = MoneyTransaction.builder()
                .senderClientId(client.getClientId())
                .receiverClientId(client.getClientId())
                .payment(Payment.builder().amount(5.00).build()).build();

        Optional<Balance> anyOptionalBalance = Optional.of(Balance.builder().amount(10.0).build());

        when(balanceDaoMock.findById(anyLong())).thenReturn(anyOptionalBalance);
        when(moneyTransactionDaoMock.save(moneyTransaction)).thenReturn(moneyTransaction);

        //when

        MoneyTransaction result = moneyTransactionServiceUnderTest.sendMoney(moneyTransaction);


        //then
        assertThat(result).isNotNull();
        verify(moneyTransactionDaoMock, times(2)).save(any(MoneyTransaction.class));
    }

    @Test
    void sendMoney_whenNoAmount_returnNull() {
        //given
        Client client;

        client = Client.builder()
                .clientId(1L).build();
        MoneyTransaction moneyTransaction = MoneyTransaction.builder()
                .senderClientId(client.getClientId())
                .receiverClientId(client.getClientId())
                .payment(Payment.builder().amount(10.00).build()).build();

        Optional<Balance> anyOptionalBalance = Optional.of(Balance.builder().amount(10.0).build());

        when(balanceDaoMock.findById(anyLong())).thenReturn(anyOptionalBalance);

        //when

        MoneyTransaction result = moneyTransactionServiceUnderTest.sendMoney(moneyTransaction);


        //then
        assertThat(result).isNull();
        verify(moneyTransactionDaoMock, times(0)).save(any(MoneyTransaction.class));
    }


}