package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MoneyTransactionController {
    @Autowired
    MoneyTransactionService moneyTransactionService;

    @Operation(summary = "Send money")
    @PostMapping(value = "/sendMoney")
    public MoneyTransaction sendMoney(@RequestBody MoneyTransaction moneyTransaction) {
        log.info("Add new transaction");
        return moneyTransactionService.sendMoney(moneyTransaction);
    }
}
