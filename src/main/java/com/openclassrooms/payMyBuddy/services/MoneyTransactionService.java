package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.dto.MoneyTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionListDto;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MoneyTransactionService {
    MoneyTransaction sendMoney(MoneyTransactionDto moneyTransactionDto);

    TransactionListDto getTransactionList(String clientEmail, Pageable pageable);
}
