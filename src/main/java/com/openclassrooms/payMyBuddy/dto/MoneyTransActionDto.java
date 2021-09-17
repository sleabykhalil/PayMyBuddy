package com.openclassrooms.payMyBuddy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoneyTransActionDto {
    private String receiverName;
    private String motive;
    private double amount;
}
