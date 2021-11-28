package com.paymybuddy.controller;

import com.paymybuddy.dto.BankTransferDTO;
import com.paymybuddy.model.BankAccount;
import com.paymybuddy.model.Connection;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.service.IBankAccountService;
import com.paymybuddy.service.ITransactionService;
import com.paymybuddy.service.IUserService;
import com.paymybuddy.service.IConnectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("")
public class MainAuthenticationController {
    private static final Logger log = LogManager.getLogger(MainAuthenticationController.class);

    private final ITransactionService transactionService;
    private final IUserService userService;
    private final IConnectionService connectionService;
    private final IBankAccountService bankAccountService;

    public MainAuthenticationController(ITransactionService transactionService, IUserService userService, IConnectionService connectionService, IBankAccountService bankAccountService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.connectionService = connectionService;
        this.bankAccountService = bankAccountService;
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String register(@ModelAttribute User user) {
        return "register";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login(Model model, @RequestParam(value = "logout",	required = false) boolean logout) {

        if(logout){
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "login";
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String home(Model model, Principal principal) {

        User user = userService.findByEmail(principal.getName());

        model.addAttribute("firstName",user.getFirstName());
        model.addAttribute("balance",user.getAccount().getBalance());

        return "home";
    }

    @RequestMapping(value = { "/listNonConnectedUsers" }, method = RequestMethod.GET)
    public String listNonConnectedUsers(ModelMap model, Principal principal) {

        User persistedUser = userService.findByEmail(principal.getName());
        List<User> nonConnectedUsers = userService.findAllNonConnectedUsersByUserId(persistedUser.getId());
        model.addAttribute("nonConnectedUsers",nonConnectedUsers);

        return "transfer :: #selectEmail";
    }


    @RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
    public String logout(Model model) {

        return "redirect:/login?logout=true";
    }

    @RequestMapping(value = { "/transfer" }, method = RequestMethod.GET)
    public String transfer(Model model, Principal principal,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        User persistedUser = userService.findByEmail(principal.getName());


        Page<Transaction> transactionPage = transactionService.findAllByUserId(persistedUser.getId(),PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("transactionPage", transactionPage);

        int totalPages = transactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        List<Connection> connections= connectionService.findAllConnectionsByUserId(persistedUser.getId());
        model.addAttribute("connections",connections);

        List<User> nonConnectedUsers = userService.findAllNonConnectedUsersByUserId(persistedUser.getId());
        model.addAttribute("nonConnectedUsers",nonConnectedUsers);

        return "transfer";
    }

    @RequestMapping(value = { "/bank-transfer" }, method = RequestMethod.GET)
    public String bankTransfer(Model model, @ModelAttribute BankTransferDTO bankTransferDTO,Principal principal) {

        User user =userService.findByEmail(principal.getName());
        bankTransferDTO.setEmail(user.getEmail());
        BankAccount bankAccount = bankAccountService.findByUser(user);
        bankTransferDTO.setBankAccount(bankAccount);

        return "bank-transfer";
    }

}
