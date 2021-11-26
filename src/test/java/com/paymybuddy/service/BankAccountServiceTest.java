package com.paymybuddy.service;

import com.paymybuddy.ConstantsTest;
import com.paymybuddy.model.Account;
import com.paymybuddy.model.BankAccount;
import com.paymybuddy.repository.AccountRepository;
import com.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.service.impl.AccountService;
import com.paymybuddy.service.impl.BankAccountService;
import com.paymybuddy.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankAccountServiceTest {


    @InjectMocks
    private BankAccountService bankAccountService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Test
    public void givenAccount_whenSaveBankAccount_thenReturnBankAccount() {

        when(bankAccountRepository.save(any())).thenReturn(ConstantsTest.bankAccount);
        BankAccount bankAccount = bankAccountService.saveBankAccount(ConstantsTest.bankAccount);
        assertNotNull(bankAccount);

    }
    @Test
    public void givenUser_whenFindByUser_thenReturnUser(){

        when(bankAccountRepository.findByUser(any())).thenReturn(ConstantsTest.bankAccount);
        BankAccount bankAccount = bankAccountService.findByUser(ConstantsTest.user);
        assertNotNull(bankAccount);

    }
}
