package com.paymybuddy.service.impl;

import com.paymybuddy.constants.Constants;
import com.paymybuddy.constants.TransactionType;
import com.paymybuddy.exception.BusinessResourceException;
import com.paymybuddy.model.*;
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
    private final ConnectionService connectionService;
    private final UserService userService;
    private final AccountService accountService;
    private final BankAccountService bankAccountService;

    public TransactionService(TransactionRepository transactionRepository, ConnectionService connectionService, UserService userService, AccountService accountService, BankAccountService bankAccountService) {
        this.transactionRepository = transactionRepository;
        this.connectionService = connectionService;
        this.userService = userService;
        this.accountService = accountService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public Page<Transaction> findAllByUserId(Long userId, Pageable pageable) {
        return transactionRepository.findAllByUSerId(userId,pageable);
    }

    @Override
    @Transactional(rollbackFor = {BusinessResourceException.class})
    public void saveTransaction(Principal principal, Long connectedUserId, BigDecimal amount, String description) {
        User user =userService.findUserAndAccountByEmail(principal.getName());
        User connectedUser = userService.getById(connectedUserId);

        if ( connectedUser.getAccount() == null  || user.getAccount().getBalance().compareTo(amount) < 0){
            throw new BusinessResourceException("Insufficient balance, please check your account before any transfer", HttpStatus.BAD_REQUEST);
        }

        Transaction transaction= new Transaction();

        Connection connection=connectionService.findConnectionByUserIdAndConnectedUserId(user.getId(),connectedUserId );
        transaction.setConnection(connection);
        transaction.setType(TransactionType.CREDIT);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transactionRepository.save(transaction);

        // Update Balance after transaction for user to subtract the amount
        Account persistedUserAccount=user.getAccount();
        persistedUserAccount.setBalance(persistedUserAccount.getBalance().subtract(amount));
        accountService.saveAccount(persistedUserAccount);

        // Update Balance after transaction for connectedUser to add the amount minus the taking percentage
        Account persistedConnectedUserAccount=connectedUser.getAccount();
        final BigDecimal amountToSend = amount.multiply(Constants.RATE_TRANSFER);
        persistedConnectedUserAccount.setBalance(persistedConnectedUserAccount.getBalance().add(amountToSend));
        accountService.saveAccount(persistedConnectedUserAccount);

    }

    @Override
    @Transactional(rollbackFor = BusinessResourceException.class)
    public void saveBankTransaction(BankAccount bankAccount, String type, Principal principal, BigDecimal amount, String description) {
        User user =userService.findUserAndAccountByEmail(principal.getName());
        boolean isDebit = TransactionType.DEBIT.name().equals(type);

        if ( isDebit &&  user.getAccount().getBalance().compareTo(amount) < 0){
            throw new BusinessResourceException("Insufficient balance, please check your account before any banking transfer", HttpStatus.BAD_REQUEST);
        }


        // verify if a banking account exist for update else create a new one
        BankAccount persistedBankAccount= bankAccountService.findByUser(user);
        if (persistedBankAccount == null){
            bankAccount.setUser(user);
            bankAccountService.saveBankAccount(bankAccount);
        }else {
            persistedBankAccount.bic(bankAccount.getBic()).iban(bankAccount.getIban());
            bankAccountService.saveBankAccount(persistedBankAccount);
        }


        Transaction transaction= new Transaction();
        Connection connection=connectionService.findConnectionByUserIdAndConnectedUserId(user.getId(),user.getId() );
        if(connection == null){
            connection =new Connection().user(user).connectedUser(user);
        }

        transaction.setConnection(connection);
        transaction.setType(TransactionType.valueOf(type));
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transactionRepository.save(transaction);

        // Update Balance after transaction for user to subtract the amount
        Account persistedUserAccount=user.getAccount();
        final BigDecimal amountToSend = amount.multiply(Constants.RATE_TRANSFER);
        persistedUserAccount.setBalance(isDebit ? persistedUserAccount.getBalance().subtract(amount) : persistedUserAccount.getBalance().add(amountToSend));
        accountService.saveAccount(persistedUserAccount);


    }


}



