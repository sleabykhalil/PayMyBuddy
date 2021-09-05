package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.services.BalanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceImplTest {

    @Mock
    BalanceDao balanceDaoMock;

    BalanceService balanceServiceUnderTest;

    @BeforeEach
    void setUp() {
        balanceServiceUnderTest = new BalanceServiceImpl(balanceDaoMock);
    }

    @Test
    void feedBalance_WhenClientIdNotFound_AddNewClientIdWithBalance() {
        //given
        when(balanceDaoMock.findById(1L)).thenReturn(Optional.empty());
        when(balanceDaoMock.save(any())).thenReturn(Balance.builder().clientId(1L).amount(10.00).build());
        //when
        Balance result = balanceServiceUnderTest.feedBalance(1L, 10.00);

        //then
        assertThat(result.getAmount()).isEqualTo(10.00);
        assertThat(result.getClientId()).isEqualTo(1L);
    }

    @Test
    void feedBalance_WhenClientIdFound_AddNewAmountToOldBalance() {
        //given
        Balance balance = Balance.builder().clientId(1L).amount(10.00).build();
        when(balanceDaoMock.findById(1L)).thenReturn(Optional.of(balance));
        when(balanceDaoMock.save(any())).thenReturn(Balance.builder().clientId(1L).amount(20.00).build());
        //when
        Balance result = balanceServiceUnderTest.feedBalance(1L, 10.00);

        //then
        assertThat(result.getAmount()).isEqualTo(20.00);
        assertThat(result.getClientId()).isEqualTo(1L);
    }
}