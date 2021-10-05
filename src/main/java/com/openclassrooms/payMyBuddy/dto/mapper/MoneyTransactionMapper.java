package com.openclassrooms.payMyBuddy.dto.mapper;

import com.openclassrooms.payMyBuddy.dto.MoneyTransactionDto;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MoneyTransactionMapper {

    @Mapping(target = "payment.motive", source = "motive")
    @Mapping(target = "payment.amount", source = "amount")
    MoneyTransaction moneyTransActionDtoToMoneyTransaction(MoneyTransactionDto moneyTransactionDto);
}
