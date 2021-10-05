package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dto.*;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import com.openclassrooms.payMyBuddy.services.MoneyTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
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
    @PostMapping(value = "/transfer", params = "transaction")
    public ModelAndView sendMoney(@RequestParam boolean transaction
            , MoneyTransactionDto moneyTransactionDto) {

        log.info("Add new transaction");
        RedirectView redirectView = new RedirectView();
        if ((moneyTransactionService.sendMoney(moneyTransactionDto)) != null) {
            redirectView.setUrl("/transfer");
            return new ModelAndView(redirectView);
        }
        redirectView.setUrl("404");
        return new ModelAndView(redirectView);
    }


    @GetMapping(value = "/transfer")
    public ModelAndView getTransactionList(Principal principal
            , @RequestParam(required = true, defaultValue = "3") int size
            , @RequestParam(required = true, defaultValue = "0") int page) {
        String viewName = "transfer";
        Map<String, Object> model = new HashMap<String, Object>();
        String clientEmail = principal.getName();


        Client client = clientService.findClientByEmail(clientEmail);
        ClientDto clientDto = clientMapper.clientToClientDto(client);
        model.put("clientDto", clientDto);

        List<Client> notFriendList = clientService.findNotFriendList(client);
        model.put("notFriendList", notFriendList);
        NewFriendDto newFriendDto = NewFriendDto.builder()
                .clientId(client.getClientId()).build();
        model.put("newFriendDto", newFriendDto);

        MoneyTransactionDto moneyTransactionDto = MoneyTransactionDto.builder()
                .senderClientId(client.getClientId())
                .motive("undefined")//TODO will be removed in next version
                .build();
        List<Client> friendsList = clientService.findAllFriends(client.getFriends());
        List<FriendEmailDto> friendsEmailList = clientMapper.friendEmailList(friendsList);
        model.put("friendsEmailList", friendsEmailList);
        model.put("moneyTransactionDto", moneyTransactionDto);

        Pageable pageable = PageRequest.of(page, size);
        TransactionListDto transactionListDto = moneyTransactionService.getTransactionList(clientEmail, pageable);
        List<PaymentDto> paymentDtoList = transactionListDto.getPaymentDtoList();

        model.put("paymentDtoList", paymentDtoList);
        model.put("numberOfTransaction", paymentDtoList.size());
        int pageNumber = transactionListDto.getPageNumber();

        List<Integer> pageNumberList = new ArrayList<>();
        for (int i = 0; i < pageNumber; i++) {
            pageNumberList.add(i);
        }

        model.put("pageNumberList", pageNumberList);

        return new ModelAndView(viewName, model);
    }

}
