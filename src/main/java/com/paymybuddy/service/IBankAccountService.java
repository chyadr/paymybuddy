package com.paymybuddy.service;

import com.paymybuddy.model.BankAccount;

import com.paymybuddy.model.User;



public interface IBankAccountService {
    BankAccount saveBankAccount (BankAccount bankAccount);
    BankAccount findByUser(User user);
}
