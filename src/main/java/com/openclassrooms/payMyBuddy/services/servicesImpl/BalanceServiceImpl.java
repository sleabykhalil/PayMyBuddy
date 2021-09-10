package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.services.BalanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {
    private final BalanceDao balanceDao;

    @Override
    public Balance feedBalanceFromBankAccount(long clientId, double amount) {
        Optional<Balance> oldBalance = balanceDao.findById(clientId);
        Balance newBalance;
        if (oldBalance.isPresent()) {
            newBalance = Balance.builder()
                    .clientId(clientId)
                    .amount(oldBalance.get().getAmount() + amount)
                    .build();
        } else {
            newBalance = Balance.builder()
                    .clientId(clientId)
                    .amount(amount)
                    .build();
        }
        return balanceDao.save(newBalance);
    }

    @Override
    public Balance feedBankAccountFromBalance(long clientId, double amount) {
        Optional<Balance> oldBalance = balanceDao.findById(clientId);
        Balance newBalance;
        if (oldBalance.isPresent() && amount <= oldBalance.get().getAmount()) {
            newBalance = Balance.builder()
                    .clientId(clientId)
                    .amount(oldBalance.get().getAmount() - amount)
                    .build();

            return balanceDao.save(newBalance);
        } else {
            log.error("Balance not enough");
            return null;
        }
    }
}
