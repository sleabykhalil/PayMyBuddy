package com.openclassrooms.payMyBuddy.dto;

import com.openclassrooms.payMyBuddy.model.Balance;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private long clientId;

    private String emailAccount;

    private String clientPassword;

    private String firstName;

    private String lastName;

    private String clientType;

    private Balance balance;

    private List<MoneyTransaction> moneyTransactions;

    private List<Client> friends;
}
