package com.paymybuddy.controller;


import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.service.impl.TransactionService;
import com.paymybuddy.service.impl.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @RequestMapping(value = { "/saveTransaction" }, method = RequestMethod.POST)
    public String saveTransaction(Principal principal, @RequestParam Long connectedUserId,@RequestParam BigDecimal amount, @RequestParam String description, Model model){

        transactionService.saveTransaction( principal,connectedUserId,amount,description);


        User persistedUser = userService.findByEmail(principal.getName());

        Page<Transaction> transactionPage = transactionService.findAllByUserId(persistedUser.getId(), PageRequest.of(0, 5));

        model.addAttribute("transactionPage", transactionPage);

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        return "home :: #tableTransaction";
    }
}
