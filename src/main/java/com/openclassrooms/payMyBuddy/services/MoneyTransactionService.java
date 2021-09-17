package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.dto.MoneyTransactionDto;
import com.openclassrooms.payMyBuddy.dto.PaymentDto;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MoneyTransactionService {
    MoneyTransaction sendMoney(MoneyTransactionDto moneyTransactionDto);

    List<PaymentDto> getTransactionList(String clientEmail);
}
