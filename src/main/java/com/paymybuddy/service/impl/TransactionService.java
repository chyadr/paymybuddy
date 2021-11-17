package com.paymybuddy.service.impl;

import com.paymybuddy.exception.BusinessResourceException;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.Connection;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.model.User;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.TransactionRepository;

import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.ITransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;


@Service
@Transactional
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;


    public TransactionService(TransactionRepository transactionRepository, ConnectionRepository connectionRepository, UserRepository userRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Transaction> findAllByUserId(Long userId, Pageable pageable) {
        return transactionRepository.findAllByUSerId(userId,pageable);
    }

    @Override
    @Transactional(rollbackFor = {BusinessResourceException.class})
    public void saveTransaction(Principal principal, Long connectedUserId, BigDecimal amount, String description) {
        User user =userRepository.findUserAndAccountByEmail(principal.getName());

        final BigDecimal amountResult = amount.add(amount).multiply(BigDecimal.valueOf(0.05));
        if (user == null || user.getAccount() == null || user.getAccount().getBalance().compareTo(amountResult) < 0){
            throw new BusinessResourceException("Insufficient balance, please check your account before any transfer", HttpStatus.BAD_REQUEST);
        }

        Transaction transaction= new Transaction();

        Connection connection=connectionRepository.findConnectionByUserIdAndConnectedUserId(user.getId(),connectedUserId );
        transaction.setConnection(connection);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transactionRepository.save(transaction);

        // Update Balance after transaction
        Account persistedAccount=user.getAccount();
        persistedAccount.setBalance(persistedAccount.getBalance().subtract(amountResult));
        accountRepository.save(persistedAccount);


        }

    }



