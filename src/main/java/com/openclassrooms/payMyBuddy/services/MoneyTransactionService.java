package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.springframework.stereotype.Service;

@Service
public interface MoneyTransactionService {
    MoneyTransaction sendMoney(MoneyTransaction moneyTransaction);
}
