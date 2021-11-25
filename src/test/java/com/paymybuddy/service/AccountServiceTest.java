package com.paymybuddy.service;

import com.paymybuddy.ConstantsTest;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.Connection;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.ConnectionRepository;
import com.paymybuddy.service.impl.AccountService;
import com.paymybuddy.service.impl.ConnectionService;
import com.paymybuddy.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {


        @InjectMocks
        private AccountService accountService;

        @Mock
        private AccountRepository accountRepository;
        @Mock
        private UserService userService;



        @Test
        public void givenAccount_whenSaveAccount_thenReturnAccount() {

            when(accountRepository.save(any())).thenReturn(ConstantsTest.account);
            Account account = accountService.saveAccount(ConstantsTest.account);
            assertNotNull(account);

        }


}
