package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.dto.NewFriendDto;
import com.openclassrooms.payMyBuddy.dto.mapper.ClientMapper;
import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    ClientMapper clientMapper;



    @Operation(summary = "Add client")
    @GetMapping(value = "/signup")
    public ModelAndView signUp() {

        log.info("Add new Client");
        ClientDto clientDto = ClientDto.builder().build();

        String viewName = "signup";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("clientDto", clientDto);
        return new ModelAndView(viewName, model);
    }

    @Operation(summary = "Add client")
    @PostMapping(value = "/signup")
    public ModelAndView signUp(ClientDto clientDto) {

        log.info("Add new Client");
        RedirectView redirectView = new RedirectView();
        Client client = clientMapper.clientDtoToClient(clientDto);
        if (clientService.addNewClient(client) != null) {
            redirectView.setUrl("/login");
            return new ModelAndView(redirectView);
        }

        redirectView.setUrl("404");
        return new ModelAndView(redirectView);
    }

    @Operation(summary = "Add friend")
    @PostMapping(value = "/transfer", params = "friend")
    public ModelAndView addFriendToClient(@RequestParam boolean friend
            , NewFriendDto newFriendDto) {

        log.info("Add new friend");
        RedirectView redirectView = new RedirectView();
        String newFriendEmail = clientService.findClientById(newFriendDto.getNewFriendId()).getEmailAccount();
        clientService.updateOrInsertFriend(newFriendEmail, newFriendDto.getClientId());

        redirectView.setUrl("/transfer");
        return new ModelAndView(redirectView);

    }

}
