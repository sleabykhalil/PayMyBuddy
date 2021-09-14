package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.model.Client;
import com.openclassrooms.payMyBuddy.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @Operation(summary = "Add new Client")
    @PostMapping(value = "/newClient")
    public Client addNewClient(@RequestBody Client client) {
        log.info("Add new client");
        return clientService.addNewClient(client);
    }

    @Operation(summary = "Assign new friend  to client")
    @PutMapping(value = "/client/addFriend")
    public Client assignFriendToClient(@RequestParam String friendEmailAccount , @RequestParam long clientId) {
        log.info("assign friend [{}] to client id [{}]",friendEmailAccount,clientId);
        return clientService.updateOrInsertFriend(friendEmailAccount, clientId);
    }


}
