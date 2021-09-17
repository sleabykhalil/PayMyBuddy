package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.FriendDto;
import com.openclassrooms.payMyBuddy.dto.MoneyTransActionDto;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.services.ClientService;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class MoneyTransactionController {
    @Autowired
    MoneyTransactionService moneyTransactionService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientMapper clientMapper;

    @Operation(summary = "Send money")
    @PostMapping(value = "/sendMoney")
    public MoneyTransaction sendMoney(@RequestBody MoneyTransaction moneyTransaction) {
        log.info("Add new transaction");
        return moneyTransactionService.sendMoney(moneyTransaction);
    }

    @GetMapping(value = "/transfer")
    public ModelAndView getTransactionList() {
        String viewName = "transfer";
        Map<String, Object> model = new HashMap<String, Object>();

        // TODO delete client Email after creating log in
        String clientEmail = "khalil@gmail.com";
        Client client = clientService.findClientByEmail(clientEmail);
        ClientDto clientDto=clientMapper.ClientToClientDto(client);
        model.put("clientDto",clientDto);

        List<Client> friendsList = clientService.findAllFriends(client.getFriends());
        List<FriendDto> friendsEmailList = clientMapper.friendEmailList(friendsList);
        model.put("friendsEmailList",friendsEmailList);
        model.put("friendToPay","");


        List<MoneyTransActionDto> moneyTransActionDtoList = moneyTransactionService.getTransactionList(clientEmail);
        model.put("moneyTransActionDtoList", moneyTransActionDtoList);
        model.put("numberOfTransaction", moneyTransActionDtoList.size());
        return new ModelAndView(viewName, model);
    }
    

    
}
