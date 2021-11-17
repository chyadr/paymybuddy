package com.paymybuddy.controller;


import com.paymybuddy.model.Connection;
import com.paymybuddy.model.User;
import com.paymybuddy.service.impl.ConnectionService;
import com.paymybuddy.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ConnectionController {

    private final ConnectionService connectionService;
    private final UserService userService;

    public ConnectionController(ConnectionService connectionService, UserService userService) {
        this.connectionService = connectionService;
        this.userService = userService;
    }

    @RequestMapping(value = { "/saveConnection" }, method = RequestMethod.POST)
    public String saveConnection(Principal principal, @RequestParam Long connectedUserId , Model model){

        connectionService.saveConnection( principal,connectedUserId);


        User persistedUser = userService.findByEmail(principal.getName());

        List<Connection> connections= connectionService.findAllConnectionsByUserId(persistedUser.getId());
        model.addAttribute("connections",connections);

        return "home :: #selectConnection";
    }
}
