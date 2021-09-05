package com.openclassrooms.payMyBuddy.services.servicesImpl;

import com.openclassrooms.payMyBuddy.dao.BalanceDao;
import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {
    private final BalanceDao balanceDao;

    @Override
    public Balance feedBalance(long clientId, double amount) {
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
}
