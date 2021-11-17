package com.paymybuddy.controller;

import com.paymybuddy.model.User;
import com.paymybuddy.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {



    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = { "/saveUser" }, method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, Model model){
        userService.saveUser(user);
        model.addAttribute("message","user Successfully Created");
        return "login";
    }
}
