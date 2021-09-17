package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.FriendDto;
import com.openclassrooms.payMyBuddy.dto.MoneyTransactionDto;
import com.openclassrooms.payMyBuddy.dto.PaymentDto;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.model.MoneyTransaction;
import com.openclassrooms.payMyBuddy.services.ClientService;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestController
public class MoneyTransactionController {
    @Autowired
    MoneyTransactionService moneyTransactionService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientMapper clientMapper;

/*    @Operation(summary = "Send money")
    @PostMapping(value = "/sendMoney")
    public MoneyTransaction sendMoney(@RequestBody MoneyTransactionDto moneyTransactionDto) {
        log.info("Add new transaction");
        return moneyTransactionService.sendMoney(moneyTransactionDto);
    }*/

    @Operation(summary = "Send money")
    @PostMapping(value = "/transfer")
    public ModelAndView sendMoney( MoneyTransactionDto moneyTransactionDto) {

        log.info("Add new transaction");
        RedirectView redirectView = new RedirectView();
        if( (moneyTransactionService.sendMoney(moneyTransactionDto)) != null){
            redirectView.setUrl("/transfer");
            return new ModelAndView(redirectView);
        };
        redirectView.setUrl("404");
        return new ModelAndView(redirectView);
    }

    @GetMapping(value = "/transfer")
    public ModelAndView getTransactionList(@RequestParam(required=false) String clientEmail) {
        String viewName = "transfer";
        Map<String, Object> model = new HashMap<String, Object>();

        // TODO delete client Email after creating log in
        if (clientEmail == null) {
            clientEmail =  "khalil@gmail.com";
        }

        Client client = clientService.findClientByEmail(clientEmail);
        ClientDto clientDto=clientMapper.ClientToClientDto(client);
        model.put("clientDto",clientDto);

        MoneyTransactionDto moneyTransactionDto = MoneyTransactionDto.builder()
                .senderClientId(client.getClientId())
                .motive("undefined")//TODO will be removed in next version
                .build();
        List<Client> friendsList = clientService.findAllFriends(client.getFriends());
        List<FriendDto> friendsEmailList = clientMapper.friendEmailList(friendsList);
        model.put("friendsEmailList",friendsEmailList);
        model.put("moneyTransactionDto",moneyTransactionDto);


        List<PaymentDto> paymentDtoList = moneyTransactionService.getTransactionList(clientEmail);
        model.put("paymentDtoList", paymentDtoList);
        model.put("numberOfTransaction", paymentDtoList.size());
        return new ModelAndView(viewName, model);
    }



}
