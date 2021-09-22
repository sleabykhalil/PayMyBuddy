package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
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

    @Operation(summary = "Add new Client")
    @PostMapping(value = "/newClient")
    public Client addNewClient(@RequestBody Client client) {
        log.info("Add new client");
        return clientService.addNewClient(client);
    }

    @Operation(summary = "Assign new friend  to client")
    @PutMapping(value = "/client/addFriend")
    public Client assignFriendToClient(@RequestParam String friendEmailAccount, @RequestParam long clientId) {
        log.info("assign friend [{}] to client id [{}]", friendEmailAccount, clientId);
        return clientService.updateOrInsertFriend(friendEmailAccount, clientId);
    }

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

}
