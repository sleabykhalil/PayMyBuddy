package com.openclassrooms.payMyBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransactionDto {

    private long senderClientId;

    private long receiverClientId;

    private  String motive;
    private double amount;

}
