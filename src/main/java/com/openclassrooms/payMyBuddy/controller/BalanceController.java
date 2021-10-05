package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.services.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    @Operation(summary = "Feed balance from accounting bank")
    @PutMapping(value = "/feedBalance")
    public Balance feedBalanceFromBankAccount(@RequestParam long clientId, @RequestParam double amount) {
        log.info("feed the balance of client id [{}]  with amount [{}] from bank account", clientId, amount);
        return balanceService.feedBalanceFromBankAccount(clientId, amount);
    }

    @Operation(summary = "Feed bank account from balance")
    @PutMapping(value = "/feedBankAccount")
    public Balance feedBankAccountFromBalance(@RequestParam long clientId, @RequestParam double amount) {
        log.info("feed the bank account of client id [{}]  with amount [{}] from Balance", clientId, amount);
        return balanceService.feedBankAccountFromBalance(clientId, amount);
    }
}
