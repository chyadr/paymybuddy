package com.paymybuddy.service;

import com.paymybuddy.model.BankAccount;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.service.impl.BankAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.security.Principal;


public interface ITransactionService {

    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    void saveTransaction( Principal principal, Long connectedUserId, BigDecimal amount, String description);

    void saveBankTransaction(BankAccount bankAccount, String type, Principal principal, BigDecimal amount, String description);

}
