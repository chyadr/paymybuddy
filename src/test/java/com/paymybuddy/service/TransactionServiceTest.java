package com.paymybuddy.service;

import com.paymybuddy.Constants;
import com.paymybuddy.exception.BusinessResourceException;
import com.paymybuddy.model.Transaction;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.repository.UserRepository;
import com.paymybuddy.service.impl.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.paymybuddy.Constants.principal;
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
        private UserRepository userRepository;
        @Mock
        private ConnectionRepository connectionRepository;
        @Mock
        private AccountRepository accountRepository;




        @Test
        public void givenUserIdAndPageable_whenFindAllByUSerId_thenReturnTransactions() {
        when(transactionRepository.findAllByUSerId(anyLong(),any())).thenReturn(Constants.pageTransaction);
        Page<Transaction> transactions= transactionService.findAllByUserId(1L, Pageable.unpaged());
        assertNotNull(transactions);
        assertEquals(transactions.getTotalPages(),1);

        }

        @Test
        public void givenPrincipalAndConnectedUserIdAndAmountAndDescription_whenSaveTransaction_thenThrowException(){

                when(userRepository.findUserAndAccountByEmail(anyString())).thenReturn(null);

                assertThrows(
                        BusinessResourceException.class,
                        () -> transactionService.saveTransaction(principal,7L, BigDecimal.valueOf(5),"fff"),
                        "Insufficient balance, please check your account before any transfer"
                );

        }

        @Test
        public void givenPrincipalAndConnectedUserIdAndAmountAndDescription_whenSaveTransaction_thenReturnNothing(){

                when(userRepository.findUserAndAccountByEmail(anyString())).thenReturn(Constants.user);
                when(connectionRepository.findConnectionByUserIdAndConnectedUserId(anyLong(),anyLong())).thenReturn(Constants.connection);
                when(transactionRepository.save(Constants.transaction)).thenReturn(Constants.transaction);
                when(accountRepository.save(Constants.account)).thenReturn(Constants.account);

                transactionService.saveTransaction(principal,7L, BigDecimal.valueOf(5),"fff");
                verify(transactionRepository,times(1)).save(Constants.transaction);
                verify(accountRepository,times(1)).save(Constants.account);

        }
}