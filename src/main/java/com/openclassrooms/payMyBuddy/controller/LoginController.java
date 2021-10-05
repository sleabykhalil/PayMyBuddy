package com.openclassrooms.payMyBuddy.controller;

import com.openclassrooms.payMyBuddy.dto.ClientDto;
import com.openclassrooms.payMyBuddy.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private ClientService clientService;

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {

        Map<String, Object> model = new HashMap<String, Object>();
        ClientDto clientDto = ClientDto.builder().build();
        model.put("clientDto", clientDto);

        String viewName = "login";

        return new ModelAndView(viewName, model);
    }
}
