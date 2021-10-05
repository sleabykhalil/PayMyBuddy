package com.openclassrooms.payMyBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionListDto {
    List<PaymentDto> paymentDtoList = new ArrayList<>();
    int pageNumber;
}
