package com.paymybuddy.service;

import com.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.security.Principal;


public interface ITransactionService {

    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    void saveTransaction(Principal principal, Long connectedUserId, BigDecimal amount, String description);
}
