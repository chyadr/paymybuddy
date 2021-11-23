package com.paymybuddy.service;

import com.paymybuddy.model.BankAccount;
import com.paymybuddy.model.Connection;
import com.paymybuddy.model.User;

import java.security.Principal;
import java.util.List;

public interface IBankAccountService {
    void saveBankAccount (BankAccount bankAccount);
    BankAccount findByUser(User user);
}
