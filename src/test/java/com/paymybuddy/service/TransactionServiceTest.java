package com.paymybuddy.service;

import com.paymybuddy.ConstantsTest;
import com.paymybuddy.exception.BusinessResourceException;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.paymybuddy.ConstantsTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

        @InjectMocks
        private TransactionService transactionService;

        @Mock
        private TransactionRepository transactionRepository;
        @Mock
        private UserService userService;
        @Mock
        private ConnectionService connectionService;
        @Mock
        private AccountService accountService;
        @Mock
        private BankAccountService bankAccountService;





        @Test
        public void givenUserIdAndPageable_whenFindAllByUSerId_thenReturnTransactions() {
        when(transactionRepository.findAllByUSerId(anyLong(),any())).thenReturn(ConstantsTest.pageTransaction);
        Page<Transaction> transactions= transactionService.findAllByUserId(1L, Pageable.unpaged());
        assertNotNull(transactions);
        assertEquals(transactions.getTotalPages(),1);

        }

        @Test
        public void givenPrincipalAndConnectedUserIdAndAmountAndDescription_whenSaveTransaction_thenThrowException(){

                when(userService.findUserAndAccountByEmail(anyString())).thenReturn(user);
                when(userService.getById(anyLong())).thenReturn(ConstantsTest.connectedUserWithoutAccount);

                assertThrows(
                        BusinessResourceException.class,
                        () -> transactionService.saveTransaction(principal,7L, BigDecimal.valueOf(5),"fff"),
                        "Insufficient balance, please check your account before any transfer"
                );

        }

        @Test
        public void givenPrincipalAndConnectedUserIdAndAmountAndDescription_whenSaveTransaction_thenReturnNothing(){

                when(userService.findUserAndAccountByEmail(anyString())).thenReturn(ConstantsTest.user);
                when(userService.getById(anyLong())).thenReturn(ConstantsTest.connectedUser);
                when(connectionService.findConnectionByUserIdAndConnectedUserId(anyLong(),anyLong())).thenReturn(ConstantsTest.connection);
                when(transactionRepository.save(ConstantsTest.transaction)).thenReturn(ConstantsTest.transaction);
                when(accountService.saveAccount(ConstantsTest.account)).thenReturn(ConstantsTest.account);

                transactionService.saveTransaction(principal,7L, BigDecimal.valueOf(5),"fff");
                verify(transactionRepository,times(1)).save(ConstantsTest.transaction);
                verify(accountService,times(2)).saveAccount(ConstantsTest.account);

        }



        @Test
        public void givenBankAccountAndTypeAndPrincipalAndAmountAndDescription_whenSaveBankTransaction_thenThrowException(){

                when(userService.findUserAndAccountByEmail(anyString())).thenReturn(userWithZeroBalance);

                assertThrows(
                        BusinessResourceException.class,
                        () -> transactionService.saveBankTransaction(bankAccount,"DEBIT",principal, BigDecimal.valueOf(5),"fff"),
                        "Insufficient balance, please check your account before any transfer"
                );

        }

        @Test
        public void givenBankAccountAndTypeAndPrincipalAndAmountAndDescription_whenSaveBankTransaction_thenReturnNothing(){

                when(userService.findUserAndAccountByEmail(anyString())).thenReturn(ConstantsTest.user);
                when(bankAccountService.findByUser(any())).thenReturn(bankAccount);
                when(bankAccountService.saveBankAccount(any())).thenReturn(bankAccount);
                when(connectionService.findConnectionByUserIdAndConnectedUserId(anyLong(),anyLong())).thenReturn(ConstantsTest.connection);
                when(transactionRepository.save(ConstantsTest.transaction)).thenReturn(ConstantsTest.transaction);
                when(accountService.saveAccount(ConstantsTest.account)).thenReturn(ConstantsTest.account);

                transactionService.saveBankTransaction(bankAccount,"CREDIT",principal, BigDecimal.valueOf(5),"fff");
                verify(transactionRepository,times(1)).save(ConstantsTest.transaction);
                verify(accountService,times(1)).saveAccount(ConstantsTest.account);

        }

        @Test
        public void givenBankAccountAndTypeAndPrincipalAndAmountAndDescription_whenSaveBankTransactionNewBankAccount_thenReturnNothing(){

                when(userService.findUserAndAccountByEmail(anyString())).thenReturn(ConstantsTest.user);
                when(bankAccountService.findByUser(any())).thenReturn(null);
                when(bankAccountService.saveBankAccount(any())).thenReturn(bankAccount);
                when(connectionService.findConnectionByUserIdAndConnectedUserId(anyLong(),anyLong())).thenReturn(ConstantsTest.connection);
                when(transactionRepository.save(ConstantsTest.transaction)).thenReturn(ConstantsTest.transaction);
                when(accountService.saveAccount(ConstantsTest.account)).thenReturn(ConstantsTest.account);

                transactionService.saveBankTransaction(bankAccount,"CREDIT",principal, BigDecimal.valueOf(5),"fff");
                verify(transactionRepository,times(1)).save(ConstantsTest.transaction);
                verify(accountService,times(1)).saveAccount(ConstantsTest.account);

        }

        @Test
        public void givenBankAccountAndTypeAndPrincipalAndAmountAndDescription_whenSaveBankTransactionNewConnection_thenReturnNothing(){

                when(userService.findUserAndAccountByEmail(anyString())).thenReturn(ConstantsTest.user);
                when(bankAccountService.findByUser(any())).thenReturn(bankAccount);
                when(bankAccountService.saveBankAccount(any())).thenReturn(bankAccount);
                when(connectionService.findConnectionByUserIdAndConnectedUserId(anyLong(),anyLong())).thenReturn(null);
                when(transactionRepository.save(ConstantsTest.transaction)).thenReturn(ConstantsTest.transaction);
                when(accountService.saveAccount(ConstantsTest.account)).thenReturn(ConstantsTest.account);

                transactionService.saveBankTransaction(bankAccount,"CREDIT",principal, BigDecimal.valueOf(5),"fff");
                verify(transactionRepository,times(1)).save(ConstantsTest.transaction);
                verify(accountService,times(1)).saveAccount(ConstantsTest.account);

        }

}