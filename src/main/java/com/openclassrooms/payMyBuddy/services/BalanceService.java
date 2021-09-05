package com.openclassrooms.payMyBuddy.services;

import com.openclassrooms.payMyBuddy.model.Balance;

public interface BalanceService {
    Balance feedBalance(long clientId, double amount);
}
