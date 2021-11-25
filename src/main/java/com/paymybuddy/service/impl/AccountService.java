package com.paymybuddy.service.impl;

import com.paymybuddy.model.Account;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.service.IAccountService;
import org.springframework.stereotype.Service;


@Service
public class AccountService implements IAccountService {


    private final AccountRepository accountRepository;


    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }
}

