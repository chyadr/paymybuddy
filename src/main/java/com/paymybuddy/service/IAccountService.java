package com.paymybuddy.service;

import com.paymybuddy.model.Account;
import com.paymybuddy.model.BankAccount;
import com.paymybuddy.model.User;

public interface IAccountService {
    Account saveAccount(Account account);
}
