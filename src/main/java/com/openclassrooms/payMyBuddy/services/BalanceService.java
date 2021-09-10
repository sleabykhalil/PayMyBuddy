package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.model.Balance;

public interface BalanceService {
    Balance feedBalanceFromBankAccount(long clientId, double amount);
    Balance feedBankAccountFromBalance(long clientId, double amount);
}
