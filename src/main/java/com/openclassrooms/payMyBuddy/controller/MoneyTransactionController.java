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

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
//@RolesAllowed({"CLIENT", "ADMIN"})
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

    //@RolesAllowed({"CLIENT", "ADMIN"})
    @Operation(summary = "Send money")
    @PostMapping(value = "/transfer")
    public ModelAndView sendMoney(MoneyTransactionDto moneyTransactionDto) {

        log.info("Add new transaction");
        RedirectView redirectView = new RedirectView();
        if ((moneyTransactionService.sendMoney(moneyTransactionDto)) != null) {
            redirectView.setUrl("/transfer");
            return new ModelAndView(redirectView);
        }
        redirectView.setUrl("404");
        return new ModelAndView(redirectView);
    }

    //@RolesAllowed({"CLIENT", "ADMIN"})
    @GetMapping(value = "/transfer")
    public ModelAndView getTransactionList(Principal principal
            , @RequestParam(required = true, defaultValue = "3") int size
            , @RequestParam(required = true, defaultValue = "0") int page) {
        //   public ModelAndView getTransactionList(@RequestParam(required = false) String clientEmail) {
        String viewName = "transfer";
        Map<String, Object> model = new HashMap<String, Object>();
        String clientEmail = principal.getName();
        // TODO delete client Email after creating log in
        if (clientEmail == null) {
            clientEmail = "khalil@gmail.com";
        }

        Client client = clientService.findClientByEmail(clientEmail);
        ClientDto clientDto = clientMapper.clientToClientDto(client);
        model.put("clientDto", clientDto);

        MoneyTransactionDto moneyTransactionDto = MoneyTransactionDto.builder()
                .senderClientId(client.getClientId())
                .motive("undefined")//TODO will be removed in next version
                .build();
        List<Client> friendsList = clientService.findAllFriends(client.getFriends());
        List<FriendDto> friendsEmailList = clientMapper.friendEmailList(friendsList);
        model.put("friendsEmailList", friendsEmailList);
        model.put("moneyTransactionDto", moneyTransactionDto);

        Pageable pageable = PageRequest.of(page, size);
        TransactionListDto transactionListDto = moneyTransactionService.getTransactionList(clientEmail, pageable);
        List<PaymentDto> paymentDtoList = transactionListDto.getPaymentDtoList();

        model.put("paymentDtoList", paymentDtoList);
        model.put("numberOfTransaction", paymentDtoList.size());
        int pageNumber = transactionListDto.getPageNumber();
/*        if (paymentDtoList.size()> 3 ) {
            pageNumber= (int) Math.ceil( paymentDtoList.size()/3);
        }*/
        List<Integer> pageNumberList = new ArrayList<>();
        for (int i = 0; i < pageNumber; i++) {
            pageNumberList.add(i);
        }

        model.put("pageNumberList", pageNumberList);

        return new ModelAndView(viewName, model);
    }

}
