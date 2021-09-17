package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.dto.MoneyTransActionDto;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MoneyTransactionService {
    MoneyTransaction sendMoney(MoneyTransaction moneyTransaction);

    List<MoneyTransActionDto> getTransactionList(String clientEmail);
}
